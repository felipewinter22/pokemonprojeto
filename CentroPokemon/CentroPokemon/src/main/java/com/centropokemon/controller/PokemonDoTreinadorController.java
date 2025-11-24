/*
 * Centro Pokémon - Controlador de Pokémon do Treinador
 * ---------------------------------------
 * @file        PokemonDoTreinadorController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.2
 * @date        23/11/2025
 * @description Endpoints REST para gerenciar a coleção de Pokémon de um treinador.
 *              Permite adicionar Pokémon da Pokédex, listar a coleção e remover Pokémon.
 */

package com.centropokemon.controller;

import com.centropokemon.model.Pokemon;
import com.centropokemon.service.CadastroPokemonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciar a coleção de Pokémon de um treinador.
 * Permite adicionar Pokémon pesquisados na Pokédex à coleção pessoal,
 * listar todos os Pokémon do treinador e remover Pokémon da coleção.
 * 
 * @author Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version 1.2
 * @since 1.0
 */
@RestController
@RequestMapping("/api/treinadores/{treinadorId}/pokemons")
public class PokemonDoTreinadorController extends BaseRestController {

    private final CadastroPokemonService cadastro;

    public PokemonDoTreinadorController(CadastroPokemonService cadastro) {
        this.cadastro = cadastro;
    }

    /**
     * Payload para adicionar um Pokémon à coleção do treinador.
     * Contém todos os dados necessários vindos da Pokédex.
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
     * Resposta com dados do Pokémon da coleção do treinador.
     * Não expõe dados sensíveis, apenas informações públicas do Pokémon.
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
     * Adiciona um Pokémon à coleção do treinador.
     * Endpoint: POST /CentroPokemon/api/treinadores/{treinadorId}/pokemons
     * 
     * Usado quando o treinador pesquisa um Pokémon na Pokédex e decide adicioná-lo.
     * Valida se o Pokémon já não está na coleção para evitar duplicatas.
     * 
     * @param treinadorId ID do treinador
     * @param req dados do Pokémon a ser adicionado
     * @return 201 CREATED com dados do Pokémon adicionado, ou erro apropriado
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
            out.message = "Pokémon adicionado à sua coleção com sucesso!";
            return created(out);
        } catch (IllegalArgumentException e) {
            String msg = e.getMessage();
            if (msg != null && msg.toLowerCase().contains("treinador")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Treinador não encontrado");
            }
            if (msg != null && (msg.toLowerCase().contains("duplicado") || msg.toLowerCase().contains("já possui"))) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Você já possui este Pokémon na sua coleção!");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(msg != null ? msg : "Dados inválidos");
        }
    }

    /**
     * Lista todos os Pokémon da coleção do treinador.
     * Endpoint: GET /CentroPokemon/api/treinadores/{treinadorId}/pokemons
     * 
     * Retorna a lista completa de Pokémon que o treinador possui,
     * incluindo o inicial e todos os adicionados via Pokédex.
     * Usado para exibir a coleção e para selecionar Pokémon em consultas/cura.
     * 
     * @param treinadorId ID do treinador
     * @return lista de Pokémon da coleção
     */
    @GetMapping
    public ResponseEntity<List<PokemonResponse>> listar(@PathVariable Integer treinadorId) {
        List<PokemonResponse> lista = cadastro.listar(treinadorId)
                .stream().map(PokemonResponse::of).toList();
        return ok(lista);
    }

    /**
     * Remove um Pokémon da coleção do treinador.
     * Endpoint: DELETE /CentroPokemon/api/treinadores/{treinadorId}/pokemons/{pokemonId}
     * 
     * Só remove se o Pokémon realmente pertencer ao treinador.
     * 
     * @param treinadorId ID do treinador
     * @param pokemonId ID do Pokémon a ser removido
     * @return 204 NO CONTENT se removido, 404 NOT FOUND se não encontrado
     */
    @DeleteMapping("/{pokemonId}")
    public ResponseEntity<Void> remover(@PathVariable Integer treinadorId, @PathVariable Integer pokemonId) {
        boolean removido = cadastro.remover(treinadorId, pokemonId);
        return removido ? noContent() : notFound();
    }
}
