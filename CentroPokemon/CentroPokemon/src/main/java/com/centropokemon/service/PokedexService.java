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
 * No estágio atual, consulta a PokeAPI via `DataInicializacao`.
 */
@Service
public class PokedexService {

    private final DataInicializacao dataInicializacao;

    /**
     * Construtor com injeção do serviço de dados da PokeAPI.
     * @param dataInicializacao serviço de carregamento da PokeAPI
     */
    public PokedexService(DataInicializacao dataInicializacao) {
        this.dataInicializacao = dataInicializacao;
    }

    /**
     * Busca um Pokémon pelo nome (inglês) usando a PokeAPI.
     * SEMPRE busca da API para garantir dados atualizados.
     * @param nome nome do Pokémon
     * @return entidade `Pokemon` ou null se não encontrado
     */
    public Pokemon buscarPokemonPorNome(String nome) {
        // SEMPRE busca da API para atualizar os dados
        return dataInicializacao.carregarPokemon(nome);
    }

    public Pokemon buscarPokemonAleatorio() {
        return dataInicializacao.carregarPokemonAleatorio();
    }

    public Pokemon buscarPokemonAleatorioPorTipo(String type) {
        return dataInicializacao.carregarPokemonAleatorioPorTipo(type);
    }

    public Pokemon buscarPokemonPorId(Integer id) {
        // SEMPRE busca da API para atualizar os dados
        return dataInicializacao.carregarPokemon(String.valueOf(id));
    }
}
