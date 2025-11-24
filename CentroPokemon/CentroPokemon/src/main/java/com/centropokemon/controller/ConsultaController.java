/*
 * Centro Pokémon - Controlador de Consultas
 * ---------------------------------------
 * @file        ConsultaController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.2
 * @date        23/11/2025
 * @description Endpoints REST para agendar e listar consultas médicas dos
 *              Pokémon de um treinador.
 */
package com.centropokemon.controller;

import com.centropokemon.model.Consulta;
import com.centropokemon.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/treinadores/{treinadorId}/consultas")
public class ConsultaController extends BaseRestController {
    private final ConsultaService service;

    public ConsultaController(ConsultaService service) {
        this.service = service;
    }

    public static class AgendarRequest {
        public Integer pokemonId;
        public String tipo;
        public String dataHora;
        public String observacoes;
    }

    public static class ConsultaResponse {
        public Integer id;
        public String tipo;
        public String observacoes;
        public String dataHora;
        public String pokemonNome;
        public String pokemonSpriteUrl;

        public static ConsultaResponse of(Consulta c) {
            ConsultaResponse r = new ConsultaResponse();
            r.id = c.getId();
            r.tipo = c.getTipo();
            r.observacoes = c.getObservacoes();
            r.dataHora = c.getDataHora().toString();
            r.pokemonNome = c.getPokemon().getNomePt();
            r.pokemonSpriteUrl = c.getPokemon().getSpriteUrl();
            return r;
        }
    }

    @PostMapping
    public ResponseEntity<ConsultaResponse> agendar(@PathVariable Integer treinadorId, @RequestBody AgendarRequest req) {
        if (req == null || req.pokemonId == null || req.tipo == null || req.dataHora == null) {
            return badRequest();
        }
        LocalDateTime dt;
        try { 
            dt = LocalDateTime.parse(req.dataHora);
            // Valida se a data não está no passado
            if (dt.isBefore(LocalDateTime.now())) {
                return badRequest();
            }
        } catch (Exception e) { 
            return badRequest(); 
        }
        Consulta c;
        try {
            c = service.agendar(treinadorId, req.pokemonId, req.tipo, dt, req.observacoes);
        } catch (IllegalArgumentException ex) {
            if (ex.getMessage().contains("Treinador")) return notFound();
            return conflict();
        }
        return created(ConsultaResponse.of(c));
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listar(@PathVariable Integer treinadorId) {
        List<ConsultaResponse> out = service.listar(treinadorId).stream().map(ConsultaResponse::of).toList();
        return ok(out);
    }
}