/*
 * Centro Pokémon - Controlador do Centro de Cura
 * ---------------------------------------
 * @file        CentroController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.2
 * @date        23/11/2025
 * @description Endpoints REST do Centro de Cura para Pokémon da coleção do treinador.
 *              Permite curar individualmente, curar todos, verificar necessidade e status.
 */
package com.centropokemon.controller;

import com.centropokemon.model.Pokemon;
import com.centropokemon.service.CentroService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/centro")
public class CentroController extends BaseRestController {

    private final CentroService centro;

    /**
     * Construtor com injeção do serviço do Centro.
     * @param centro serviço de domínio do Centro
     */
    public CentroController(CentroService centro) {
        this.centro = centro;
    }

    /**
     * Cura um Pokémon específico da coleção do treinador.
     * Endpoint: POST /treinadores/{treinadorId}/pokemons/{pokemonId}/curar
     * 
     * Restaura a vida do Pokémon ao máximo. Só funciona se o Pokémon
     * realmente pertencer ao treinador.
     *
     * @param treinadorId ID do treinador
     * @param pokemonId ID do Pokémon da coleção do treinador
     * @return 200 OK com Pokémon curado, ou 404 NOT FOUND se não pertence ao treinador
     */
    @PostMapping("/treinadores/{treinadorId}/pokemons/{pokemonId}/curar")
    public ResponseEntity<Pokemon> curar(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        return executeOperation(() -> centro.curar(treinadorId, pokemonId));
    }

    /**
     * Cura todos os Pokémon da coleção do treinador.
     * Endpoint: POST /treinadores/{treinadorId}/pokemons/curar-todos
     * 
     * Restaura a vida de todos os Pokémon da coleção ao máximo.
     * Útil para curar toda a equipe de uma vez.
     *
     * @param treinadorId ID do treinador
     * @return 200 OK com lista de todos os Pokémon curados
     */
    @PostMapping("/treinadores/{treinadorId}/pokemons/curar-todos")
    public ResponseEntity<List<Pokemon>> curarTodos(@PathVariable Integer treinadorId) {
        return ok(centro.curarTodos(treinadorId));
    }

    /**
     * Verifica se um Pokémon da coleção precisa de cura.
     * Endpoint: GET /treinadores/{treinadorId}/pokemons/{pokemonId}/precisa-curar
     * 
     * Retorna true se a vida atual do Pokémon está abaixo da vida máxima.
     *
     * @param treinadorId ID do treinador
     * @param pokemonId ID do Pokémon da coleção
     * @return 200 OK com {"precisaCurar": true|false}, ou 404 NOT FOUND se não pertence
     */
    @GetMapping("/treinadores/{treinadorId}/pokemons/{pokemonId}/precisa-curar")
    public ResponseEntity<Map<String, Object>> precisaCurar(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        return executeOperation(() -> mapOf("precisaCurar", centro.precisaCurar(treinadorId, pokemonId)));
    }

    /**
     * Obtém estatísticas da coleção de Pokémon do treinador.
     * Endpoint: GET /treinadores/{treinadorId}/status
     * 
     * Retorna o total de Pokémon na coleção e quantos precisam de cura.
     * Útil para exibir resumo no dashboard do treinador.
     *
     * @param treinadorId ID do treinador
     * @return 200 OK com {"totalPokemons": N, "precisamCura": M}
     */
    @GetMapping("/treinadores/{treinadorId}/status")
    public ResponseEntity<Map<String, Object>> status(@PathVariable Integer treinadorId) {
        long total = centro.contarPokemonsTreinador(treinadorId);
        long precisam = centro.contarPokemonsQuePrecisamCura(treinadorId);
        return ok(mapOf("totalPokemons", total, "precisamCura", precisam));
    }
}