/*
 * Centro Pokémon - Controlador do Centro de Cura
 * ---------------------------------------
 * @file        CentroController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        2025-11-19
 * @description Endpoints REST do Centro de Cura: curar, curar todos,
 *              checar necessidade de cura e status agregado.
 */
package com.centropokemon.controller;

import com.centropokemon.model.Pokemon;
import com.centropokemon.service.CentroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Centro Pokémon - Controlador do Centro de Cura
 * ----------------------------------------------
 * Controlador REST responsável pelos endpoints do Centro de Cura
 * (curar, curar todos, checar necessidade de cura, status).
 *
 * Base: "/CentroPokemon/api/centro".
 *
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @since       1.0
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/CentroPokemon/api/centro")
public class CentroController {

    private final CentroService centro;

    /**
     * Construtor com injeção do serviço do Centro.
     * @param centro serviço de domínio do Centro
     */
    public CentroController(CentroService centro) {
        this.centro = centro;
    }

    /**
     * Curar um Pokémon específico do treinador.
     * Endpoint: POST /treinadores/{treinadorId}/pokemons/{pokemonId}/curar
     *
     * @param treinadorId id do treinador
     * @param pokemonId   id do Pokémon do treinador
     * @return Pokémon atualizado em caso de sucesso; 404 se não pertence
     */
    @PostMapping("/treinadores/{treinadorId}/pokemons/{pokemonId}/curar")
    public ResponseEntity<Pokemon> curar(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        try {
            Pokemon p = centro.curar(treinadorId, pokemonId);
            return ResponseEntity.ok(p);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Curar todos os Pokémon do treinador.
     * Endpoint: POST /treinadores/{treinadorId}/pokemons/curar-todos
     *
     * @param treinadorId id do treinador
     * @return lista de Pokémon atualizados
     */
    @PostMapping("/treinadores/{treinadorId}/pokemons/curar-todos")
    public ResponseEntity<List<Pokemon>> curarTodos(@PathVariable Integer treinadorId) {
        List<Pokemon> lista = centro.curarTodos(treinadorId);
        return ResponseEntity.ok(lista);
    }

    /**
     * Checar se o Pokémon precisa de cura.
     * Endpoint: GET /treinadores/{treinadorId}/pokemons/{pokemonId}/precisa-curar
     *
     * @param treinadorId id do treinador
     * @param pokemonId   id do Pokémon do treinador
     * @return mapa {"precisaCurar": true|false}; 404 se não pertence
     */
    @GetMapping("/treinadores/{treinadorId}/pokemons/{pokemonId}/precisa-curar")
    public ResponseEntity<Map<String, Boolean>> precisaCurar(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        try {
            boolean precisa = centro.precisaCurar(treinadorId, pokemonId);
            Map<String, Boolean> out = new HashMap<>();
            out.put("precisaCurar", precisa);
            return ResponseEntity.ok(out);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Status agregado dos Pokémon do treinador.
     * Endpoint: GET /treinadores/{treinadorId}/status
     *
     * @param treinadorId id do treinador
     * @return mapa {"totalPokemons": N, "precisamCura": M}
     */
    @GetMapping("/treinadores/{treinadorId}/status")
    public ResponseEntity<Map<String, Long>> status(@PathVariable Integer treinadorId) {
        long total = centro.contarPokemonsTreinador(treinadorId);
        long precisam = centro.contarPokemonsQuePrecisamCura(treinadorId);
        Map<String, Long> out = new HashMap<>();
        out.put("totalPokemons", total);
        out.put("precisamCura", precisam);
        return ResponseEntity.ok(out);
    }
}