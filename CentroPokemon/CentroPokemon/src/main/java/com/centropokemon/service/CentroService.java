/*
 * Centro Pokémon - Serviço do Centro de Cura
 * ---------------------------------------
 * @file        CentroService.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        23/11/2025
 * @description Regras de domínio para tratar Pokémon do treinador:
 *              curar um, curar todos, verificar necessidade e contagens.
 */
package com.centropokemon.service;

import com.centropokemon.model.Pokemon;
import com.centropokemon.repository.PokemonRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CentroService {

    private final PokemonRepository pokemons;

    public CentroService(PokemonRepository pokemons) {
        this.pokemons = pokemons;
    }

    /**
     * Cura um Pokémon específico do treinador, restaurando sua vida ao máximo.
     * @param treinadorId identificador do treinador
     * @param pokemonId identificador do Pokémon
     * @return Pokémon curado
     * @throws IllegalArgumentException se o Pokémon não pertence ao treinador
     */
    public Pokemon curar(Integer treinadorId, Integer pokemonId) {
        Optional<Pokemon> pOpt = pokemons.findByIdAndTreinadorId(pokemonId, treinadorId);
        if (pOpt.isEmpty()) throw new IllegalArgumentException("Pokémon não pertence ao treinador");
        Pokemon p = pOpt.get();
        p.tratar();
        return pokemons.save(p);
    }

    /**
     * Cura todos os Pokémon de um treinador.
     * @param treinadorId identificador do treinador
     * @return lista de Pokémon curados
     */
    public List<Pokemon> curarTodos(Integer treinadorId) {
        List<Pokemon> lista = pokemons.findByTreinadorId(treinadorId);
        if (lista.isEmpty()) return lista;
        for (Pokemon p : lista) {
            p.tratar();
        }
        return new ArrayList<>(pokemons.saveAll(lista));
    }

    /**
     * Verifica se um Pokémon precisa de cura.
     * @param treinadorId identificador do treinador
     * @param pokemonId identificador do Pokémon
     * @return true se precisa de cura, false caso contrário
     * @throws IllegalArgumentException se o Pokémon não pertence ao treinador
     */
    public boolean precisaCurar(Integer treinadorId, Integer pokemonId) {
        Optional<Pokemon> pOpt = pokemons.findByIdAndTreinadorId(pokemonId, treinadorId);
        if (pOpt.isEmpty()) throw new IllegalArgumentException("Pokémon não pertence ao treinador");
        return pOpt.get().precisaCurar();
    }

    /**
     * Conta o total de Pokémon de um treinador.
     * @param treinadorId identificador do treinador
     * @return quantidade de Pokémon
     */
    public long contarPokemonsTreinador(Integer treinadorId) {
        return pokemons.countByTreinadorId(treinadorId);
    }

    /**
     * Conta quantos Pokémon de um treinador precisam de cura.
     * @param treinadorId identificador do treinador
     * @return quantidade de Pokémon que precisam de cura
     */
    public long contarPokemonsQuePrecisamCura(Integer treinadorId) {
        List<Pokemon> lista = pokemons.findByTreinadorId(treinadorId);
        return lista.stream().filter(p -> p.getVidaAtual() != null && p.getVidaMaxima() != null && p.getVidaAtual() < p.getVidaMaxima()).count();
    }
}