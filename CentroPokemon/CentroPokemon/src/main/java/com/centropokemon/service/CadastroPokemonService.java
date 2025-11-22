/*
 * Centro Pokémon - Serviço de Cadastro de Pokémon do Treinador
 * ---------------------------------------
 * @file        CadastroPokemonService.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        2025-11-19
 * @description Regras de domínio para cadastrar, listar e remover Pokémon
 *              associados a um treinador.
 */

package com.centropokemon.service;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.Treinador;
import com.centropokemon.model.Tipo;
import com.centropokemon.repository.PokemonRepository;
import com.centropokemon.repository.TreinadorRepository;
import com.centropokemon.repository.TipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Serviço de regras para cadastro de Pokémon do treinador.
 */
@Service
public class CadastroPokemonService {

    private final PokemonRepository pokemons;
    private final TreinadorRepository treinadores;
    private final TipoRepository tiposRepo;

    /**
     * Construtor com repositórios necessários.
     */
    public CadastroPokemonService(PokemonRepository pokemons, TreinadorRepository treinadores, TipoRepository tiposRepo) {
        this.pokemons = pokemons;
        this.treinadores = treinadores;
        this.tiposRepo = tiposRepo;
    }

    /**
     * Cadastra um Pokémon para o treinador com validações básicas.
     * @param treinadorId id do treinador
     * @param pokeApiId id externo (opcional)
     * @param nomePt nome em português
     * @param nomeEn nome em inglês
     * @param spriteUrl url do sprite
     * @param vidaAtual vida atual (default 100)
     * @param vidaMaxima vida máxima (default 100)
     * @return Pokémon persistido
     * @throws IllegalArgumentException quando treinador não existe ou duplicado por pokeApiId
     */
    public Pokemon cadastrar(Integer treinadorId, Integer pokeApiId, String nomePt, String nomeEn, String spriteUrl,
                             Integer vidaAtual, Integer vidaMaxima) {
        Optional<Treinador> tOpt = treinadores.findById(treinadorId);
        if (tOpt.isEmpty()) throw new IllegalArgumentException("Treinador não encontrado: " + treinadorId);
        if (pokeApiId != null && pokemons.buscarPorTreinadorIdEPokeApiId(treinadorId, pokeApiId).isPresent()) {
            throw new IllegalArgumentException("Pokémon já cadastrado para este treinador com pokeApiId: " + pokeApiId);
        }
        Treinador treinador = tOpt.get();
        Pokemon p = new Pokemon();
        p.setTreinador(treinador);
        p.setPokeApiId(pokeApiId);
        p.setNomePt(nomePt);
        p.setNomeEn(nomeEn);
        p.setSpriteUrl(spriteUrl);
        p.setVidaAtual(vidaAtual != null ? vidaAtual : 100);
        p.setVidaMaxima(vidaMaxima != null ? vidaMaxima : 100);
        return pokemons.save(p);
    }

    /**
     * Cadastra um Pokémon com validações completas.
     *
     * @param treinadorId id do treinador
     * @param pokeApiId id externo opcional
     * @param nomePt nome em português obrigatório
     * @param nomeEn nome em inglês opcional
     * @param spriteUrl URL obrigatória de imagem http(s)
     * @param vidaAtual vida atual (>=1)
     * @param vidaMaxima vida máxima (>=1)
     * @param nivel nível do Pokémon (>=1)
     * @param habilidades lista de habilidades (opcional)
     * @param tipos lista de tipos em pt/en
     * @return Pokémon persistido
     */
    public Pokemon cadastrarCompleto(Integer treinadorId, Integer pokeApiId, String nomePt, String nomeEn, String spriteUrl,
                                     Integer vidaAtual, Integer vidaMaxima, Integer nivel, List<String> habilidades,
                                     List<String> tipos) {
        Optional<Treinador> tOpt = treinadores.findById(treinadorId);
        if (tOpt.isEmpty()) throw new IllegalArgumentException("Treinador não encontrado");
        if (pokeApiId != null && pokemons.buscarPorTreinadorIdEPokeApiId(treinadorId, pokeApiId).isPresent()) {
            throw new IllegalArgumentException("Duplicado: pokeApiId já cadastrado");
        }
        if (nomePt == null || nomePt.trim().isEmpty()) throw new IllegalArgumentException("Nome é obrigatório");
        if (spriteUrl == null || !spriteUrl.trim().toLowerCase().startsWith("http")) throw new IllegalArgumentException("Imagem inválida");
        if (tipos == null || tipos.isEmpty()) throw new IllegalArgumentException("Selecione ao menos um tipo");
        int vAtual = vidaAtual != null ? vidaAtual : 100;
        int vMax = vidaMaxima != null ? vidaMaxima : 100;
        int lvl = nivel != null ? nivel : 1;
        if (vAtual < 1 || vMax < 1 || lvl < 1) throw new IllegalArgumentException("Valores inválidos: vida/nivel");

        Treinador treinador = tOpt.get();
        Pokemon p = new Pokemon();
        p.setTreinador(treinador);
        p.setPokeApiId(pokeApiId);
        p.setNomePt(nomePt.trim());
        p.setNomeEn((nomeEn == null || nomeEn.isBlank()) ? nomePt.trim() : nomeEn.trim());
        p.setSpriteUrl(spriteUrl.trim());
        p.setVidaAtual(vAtual);
        p.setVidaMaxima(vMax);
        p.setNivel(lvl);
        p.setHabilidades(habilidades != null ? new ArrayList<>(habilidades) : new ArrayList<>());

        List<Tipo> resolved = tipos.stream()
                .map(this::resolverTipo)
                .collect(Collectors.toList());
        p.setTipos(resolved);

        return pokemons.save(p);
    }

    private Tipo resolverTipo(String valor) {
        if (valor == null || valor.isBlank()) {
            Tipo t = new Tipo();
            t.setFromApiType("normal");
            return tiposRepo.save(t);
        }
        String v = valor.trim();
        return tiposRepo.findByNomePtIgnoreCase(v)
                .or(() -> tiposRepo.findByNomeEnIgnoreCase(v))
                .orElseGet(() -> {
                    Tipo t = new Tipo();
                    t.setFromApiType(v);
                    return tiposRepo.save(t);
                });
    }

    /**
     * Lista todos os Pokémon de um treinador.
     * @param treinadorId id do treinador
     * @return lista de Pokémon
     */
    public List<Pokemon> listar(Integer treinadorId) {
        return pokemons.buscarPorTreinadorId(treinadorId);
    }

    /**
     * Remove um Pokémon do treinador se pertencer a ele.
     * @param treinadorId id do treinador
     * @param pokemonId id do Pokémon
     * @return true se removido, false caso contrário
     */
    public boolean remover(Integer treinadorId, Integer pokemonId) {
        Optional<Pokemon> pOpt = pokemons.findByIdAndTreinadorId(pokemonId, treinadorId);
        if (pOpt.isEmpty()) return false;
        pokemons.delete(pOpt.get());
        return true;
    }
}
