/*
 * Centro Pokémon - Controlador de Pokémon do Treinador
 * ---------------------------------------
 * @file        PokemonDoTreinadorController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        2025-11-19
 * @description Endpoints REST para cadastro e listagem de Pokémon de um treinador.
 */

package com.centropokemon.controller;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.Treinador;
import com.centropokemon.service.CadastroPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Controlador REST para gerenciar os Pokémon cadastrados por um treinador.
 */
@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/CentroPokemon/api/treinadores/{treinadorId}/pokemons")
public class PokemonDoTreinadorController {

    private final CadastroPokemonService cadastro;

    public PokemonDoTreinadorController(CadastroPokemonService cadastro) {
        this.cadastro = cadastro;
    }

    /**
     * Payload de cadastro de Pokémon do treinador.
     */
    public static class CadastroPokemonRequest {
        public Integer pokeApiId;
        public String nomePt;
        public String nomeEn;
        public String spriteUrl;
        public Integer vidaAtual;
        public Integer vidaMaxima;
        public Integer nivel;
        public List<String> habilidades;
        public List<String> tipos;
    }

    /**
     * Resposta segura do Pokémon com vínculo ao treinador.
     */
    public static class PokemonResponse {
        public Integer id;
        public Integer pokeApiId;
        public String nomePt;
        public String nomeEn;
        public String spriteUrl;
        public Integer vidaAtual;
        public Integer vidaMaxima;
        public Integer nivel;
        public List<String> habilidades;
        public List<String> tipos;
        public String message;

        public static PokemonResponse of(Pokemon p) {
            PokemonResponse r = new PokemonResponse();
            r.id = p.getId();
            r.pokeApiId = p.getPokeApiId();
            r.nomePt = p.getNomePt();
            r.nomeEn = p.getNomeEn();
            r.spriteUrl = p.getSpriteUrl();
            r.vidaAtual = p.getVidaAtual();
            r.vidaMaxima = p.getVidaMaxima();
            r.nivel = p.getNivel();
            r.habilidades = p.getHabilidades();
            r.tipos = p.getTipos() != null ? p.getTipos().stream().map(t -> t.getNomePt()).toList() : List.of();
            return r;
        }
    }

    /**
     * Endpoint: POST /CentroPokemon/api/treinadores/{treinadorId}/pokemons
     * Cadastra um Pokémon para o treinador.
     */
    @PostMapping
    public ResponseEntity<?> cadastrar(@PathVariable Integer treinadorId,
                                       @RequestBody CadastroPokemonRequest req) {
        if (req == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payload ausente");
        }
        if (req.nomePt == null || req.nomePt.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome é obrigatório");
        }
        if (req.spriteUrl == null || !req.spriteUrl.trim().toLowerCase().startsWith("http")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Imagem inválida");
        }
        if (req.tipos == null || req.tipos.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Selecione ao menos um tipo");
        }
        try {
            Pokemon saved = cadastro.cadastrarCompleto(
                    treinadorId,
                    req.pokeApiId,
                    req.nomePt,
                    req.nomeEn,
                    req.spriteUrl,
                    req.vidaAtual,
                    req.vidaMaxima,
                    req.nivel,
                    req.habilidades,
                    req.tipos
            );
            PokemonResponse out = PokemonResponse.of(saved);
            out.message = "Pokémon cadastrado com sucesso";
            return ResponseEntity.status(HttpStatus.CREATED).body(out);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.toLowerCase().contains("treinador")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treinador não encontrado");
            }
            if (msg != null && msg.toLowerCase().contains("duplicado")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Pokémon já cadastrado");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg != null ? msg : "Dados inválidos");
        }
    }

    /**
     * Endpoint: GET /CentroPokemon/api/treinadores/{treinadorId}/pokemons
     * Lista todos os Pokémon cadastrados pelo treinador.
     */
    @GetMapping
    public ResponseEntity<List<PokemonResponse>> listar(@PathVariable Integer treinadorId) {
        List<PokemonResponse> lista = cadastro.listar(treinadorId)
                .stream().map(PokemonResponse::of).toList();
        return ResponseEntity.ok(lista);
    }

    /**
     * Endpoint: DELETE /CentroPokemon/api/treinadores/{treinadorId}/pokemons/{pokemonId}
     * Remove um Pokémon do treinador.
     */
    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> remover(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        boolean ok = cadastro.remover(treinadorId, pokemonId);
        return ok ? ResponseEntity.noContent().build() : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
