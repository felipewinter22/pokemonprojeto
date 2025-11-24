/*
 * Centro Pokémon - Controlador da Pokédex
 * ---------------------------------------
 * @file        PokedexController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.3
 * @date        23/11/2025
 * @description Controlador responsável pelos endpoints da Pokédex via API.
 */

package com.centropokemon.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import com.centropokemon.service.PokedexService;
import com.centropokemon.exception.PokemonNotFoundException;
import com.centropokemon.model.Pokemon;

/**
 * Controlador REST da Pokédex.
 * Exponde endpoints para consulta de Pokémon via API.
 */
@RestController
@RequestMapping({"/CentroPokemon/api/pokemons", "/api/pokemons"})
public class PokedexController extends BaseRestController {

    private final PokedexService service;

    public PokedexController(PokedexService service) {
        this.service = service;
    }

    /**
     * Busca um Pokémon pelo nome (inglês) e retorna a entidade.
     *
     * @param nome nome do Pokémon (inglês)
     * @return resposta HTTP com o Pokémon encontrado
     * @throws PokemonNotFoundException quando não é encontrado
     */
    @GetMapping("/{nome}")
    public ResponseEntity<Pokemon> buscarPokemon(@PathVariable String nome) {
        Pokemon pokemon = service.buscarPokemonPorNome(nome);
        return pokemon != null ? ok(pokemon) : notFound();
    }

    @GetMapping("/random")
    public ResponseEntity<Pokemon> aleatorio() {
        Pokemon pokemon = service.buscarPokemonAleatorio();
        return pokemon != null ? ok(pokemon) : notFound();
    }

    @GetMapping("/type/{type}/random")
    public ResponseEntity<Pokemon> aleatorioPorTipo(@PathVariable String type) {
        Pokemon pokemon = service.buscarPokemonAleatorioPorTipo(type);
        return pokemon != null ? ok(pokemon) : notFound();
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Pokemon> buscarPorId(@PathVariable Integer id) {
        Pokemon pokemon = service.buscarPokemonPorId(id);
        return pokemon != null ? ok(pokemon) : notFound();
    }
}
