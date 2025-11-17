/*
 * Centro Pokémon - Exceção Pokémon não encontrado
 * ---------------------------------------
 * @file        PokemonNotFoundException.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-16
 * @description Exceção para casos em que um Pokémon não é encontrado na Pokédex.
 */

package com.centropokemon.exception;

/**
 * Exceção de domínio lançada quando um Pokémon não é encontrado.
 */
public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }
}