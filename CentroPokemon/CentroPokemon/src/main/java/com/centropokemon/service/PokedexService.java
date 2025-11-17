/*
 * Centro Pokémon - Serviço da Pokédex
 * ---------------------------------------
 * @file        PokedexService.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-16
 * @description Serviço de aplicação para operações da Pokédex.
 */

package com.centropokemon.service;

import org.springframework.stereotype.Service;
import com.centropokemon.model.Pokemon;

/**
 * Serviço responsável por orquestrar a busca de Pokémons.
 * Implementação será expandida com repositórios e cache.
 */
@Service
public class PokedexService {

    /**
     * Busca um Pokémon pelo nome.
     * @param nome nome do Pokémon
     * @return entidade `Pokemon` ou null se não encontrado
     */
    public Pokemon buscarPokemonPorNome(String nome) {
        return null;
    }
}