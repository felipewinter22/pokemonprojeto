/*
 * Centro Pokémon - Repositório de Status
 * ---------------------------------------
 * @file        PokemonStatsRepository.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-17
 * @description Interface de repositório JPA para operações de persistência de atributos (stats) de Pokémon.
 */

package com.centropokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.PokemonStats;

/**
 * Repositório JPA para a entidade {@link PokemonStats}.
 * Fornece métodos de consulta por Pokémon e por ID de Pokémon.
 */
@Repository
public interface PokemonStatsRepository extends JpaRepository<PokemonStats, Integer> {

    /**
     * Busca os atributos (stats) pelo Pokémon associado.
     * @param pokemon entidade Pokémon
     * @return Optional com os stats, se encontrado
     */
    Optional<PokemonStats> findByPokemon(Pokemon pokemon);

    /**
     * Busca os atributos (stats) pelo ID do Pokémon associado.
     * @param pokemonId identificador do Pokémon
     * @return Optional com os stats, se encontrado
     */
    Optional<PokemonStats> findByPokemonId(Integer pokemonId);

    /**
     * Alias em português para {@code findByPokemon}.
     * @param pokemon entidade Pokémon
     * @return Optional com os stats, se encontrado
     */
    default Optional<PokemonStats> buscarPorPokemon(Pokemon pokemon) {
        return findByPokemon(pokemon);
    }

    /**
     * Alias em português para {@code findByPokemonId}.
     * @param pokemonId identificador do Pokémon
     * @return Optional com os stats, se encontrado
     */
    default Optional<PokemonStats> buscarPorPokemonId(Integer pokemonId) {
        return findByPokemonId(pokemonId);
    }
}