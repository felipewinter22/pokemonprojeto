/*
 * Centro Pokémon - Controlador de Treinadores
 * ---------------------------------------
 * @file        TreinadorController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        23/11/2025
 * @description Endpoints REST para cadastro e autenticação de Treinadores.
 *              Suporta cadastro com Pokémon inicial (starter).
 */

package com.centropokemon.controller;

import com.centropokemon.model.Treinador;
import com.centropokemon.service.TreinadorService;
import com.centropokemon.service.CadastroPokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException;

/**
 * Controlador REST para o ciclo de vida do {@link Treinador}.
 * Exponde endpoints para cadastro e login.
 */
@RestController
@RequestMapping("/api/treinadores")
public class TreinadorController extends BaseRestController {

    private final TreinadorService service;
    private final CadastroPokemonService cadastroPokemon;

    /**
     * Construtor com injeção do serviço de treinadores.
     * @param service serviço de domínio
     */
    public TreinadorController(TreinadorService service, CadastroPokemonService cadastroPokemon) {
        this.service = service;
        this.cadastroPokemon = cadastroPokemon;
    }

    /**
     * Requisição de cadastro de treinador.
     * Campos obrigatórios: nome, usuario, email, senha.
     */
    public static class CadastroRequest {
        public String nome;
        public String usuario;
        public String email;
        public String senha;
        public String telefone;
        public Integer starterId;
        public String starterName;
        public String starterSpriteUrl;
    }

    /**
     * Requisição de login de treinador.
     * Aceita usuário OU e-mail, mais senha.
     */
    public static class LoginRequest {
        public String usuarioOuEmail;
        public String senha;
    }

    /**
     * Resposta segura do treinador (sem expor hash de senha).
     */
    public static class TreinadorResponse {
        public Integer id;
        public String nome;
        public String usuario;
        public String email;
        public String telefone;
        public Boolean ativo;

        public static TreinadorResponse of(Treinador t) {
            TreinadorResponse r = new TreinadorResponse();
            r.id = t.getId();
            r.nome = t.getNome();
            r.usuario = t.getUsuario();
            r.email = t.getEmail();
            r.telefone = t.getTelefone();
            r.ativo = t.getAtivo();
            return r;
        }
    }

    /**
     * Endpoint: POST /api/treinadores/cadastrar
     * Cadastra um novo treinador.
     * @param req dados de cadastro
     * @return entidade persistida (safe response) e HTTP 201
     */
    @PostMapping("/cadastrar")
    public ResponseEntity<TreinadorResponse> cadastrar(@RequestBody CadastroRequest req) {
        if (req == null || req.nome == null || req.usuario == null || req.email == null || req.senha == null
                || req.nome.isBlank() || req.usuario.isBlank() || req.email.isBlank() || req.senha.isBlank()) {
            return badRequest();
        }
        Treinador t;
        try {
            t = service.cadastrar(req.nome, req.usuario, req.email, req.senha, req.telefone);
        } catch (IllegalArgumentException | DataIntegrityViolationException ex) {
            return conflict();
        }
        if (req.starterId != null && cadastroPokemon != null) {
            Integer id = req.starterId;
            String name = req.starterName != null ? req.starterName : defaultStarterName(id);
            String sprite = req.starterSpriteUrl != null ? req.starterSpriteUrl : defaultSpriteUrl(id);
            try {
                cadastroPokemon.cadastrar(t.getId(), id, name, name, sprite, null, null);
            } catch (IllegalArgumentException ignored) {}
        }
        return created(TreinadorResponse.of(t));
    }

    private String defaultStarterName(Integer id) {
        if (id == null) return "Inicial";
        return switch (id) {
            case 1 -> "Bulbasaur";
            case 4 -> "Charmander";
            case 7 -> "Squirtle";
            default -> "Inicial";
        };
    }

    /**
     * Retorna a URL da imagem local do Pokémon inicial.
     * Usa as imagens salvas localmente na pasta /imagens/.
     * 
     * @param id ID do Pokémon inicial (1=Bulbasaur, 4=Charmander, 7=Squirtle)
     * @return URL local da imagem
     */
    private String defaultSpriteUrl(Integer id) {
        if (id == null) return "/imagens/pokebola.png";
        return switch (id) {
            case 1 -> "/imagens/bulbasauro.png";
            case 4 -> "/imagens/charmander.png";
            case 7 -> "/imagens/squirtle.png";
            default -> "/imagens/pokebola.png";
        };
    }

    /**
     * Endpoint: POST /api/treinadores/login
     * Autentica um treinador por usuário ou e-mail.
     * @param req dados de login
     * @return 200 com treinador (safe response) ou 401
     */
    @PostMapping("/login")
    public ResponseEntity<TreinadorResponse> login(@RequestBody LoginRequest req) {
        if (req == null || req.usuarioOuEmail == null || req.senha == null) {
            return badRequest();
        }
        return service.autenticar(req.usuarioOuEmail, req.senha)
                .map(t -> ok(TreinadorResponse.of(t)))
                .orElseGet(this::unauthorized);
    }
}