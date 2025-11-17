/*
 * Centro Pokémon - Repositório de Descrições
 * ---------------------------------------
 * @file        PokemonDescricaoRepository.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-17
 * @description Interface de repositório JPA para operações de persistência de descrições de Pokémon.
 */

package com.centropokemon.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.PokemonDescricao;

/**
 * Repositório JPA para a entidade {@link PokemonDescricao}.
 * Oferece consultas por Pokémon e por ID de Pokémon.
 */
@Repository
public interface PokemonDescricaoRepository extends JpaRepository<PokemonDescricao, Integer> {

    /**
     * Busca todas as descrições associadas ao Pokémon informado.
     * @param pokemon entidade Pokémon
     * @return lista de descrições
     */
    List<PokemonDescricao> findByPokemon(Pokemon pokemon);

    /**
     * Busca todas as descrições pelo ID do Pokémon associado.
     * @param pokemonId identificador do Pokémon
     * @return lista de descrições
     */
    List<PokemonDescricao> findByPokemonId(Integer pokemonId);

    /**
     * Alias em português para {@code findByPokemon}.
     * @param pokemon entidade Pokémon
     * @return lista de descrições
     */
    default List<PokemonDescricao> buscarPorPokemon(Pokemon pokemon) {
        return findByPokemon(pokemon);
    }

    /**
     * Alias em português para {@code findByPokemonId}.
     * @param pokemonId identificador do Pokémon
     * @return lista de descrições
     */
    default List<PokemonDescricao> buscarPorPokemonId(Integer pokemonId) {
        return findByPokemonId(pokemonId);
    }
}