/*
 * Centro Pokémon - Controlador de Rotas Públicas
 * ---------------------------------------
 * @file        SiteController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        2025-11-19
 * @description Controlador MVC que expõe as rotas públicas do site e encaminha
 *              para os arquivos estáticos correspondentes.
 */
package com.centropokemon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Centro Pokémon - Controlador de Rotas Públicas
 * ----------------------------------------------
 * Controlador MVC que expõe as rotas públicas do site e encaminha
 * para os arquivos estáticos correspondentes.
 *
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @since       1.0
 */
@Controller
public class SiteController {

    /**
     * Home/Landing page.
     *
     * Rotas: "/", "/Pokemon", "/inicio".
     * Encaminha (forward) para `Pokemon.html`.
     *
     * @return caminho do recurso estático
     * @see <a href="/Pokemon.html">Pokemon.html</a>
     */
    @GetMapping({"/", "/Pokemon", "/inicio"})
    public String home() {
        return "forward:/Pokemon.html";
    }

    /**
     * Página da Pokédex.
     *
     * Rotas: "/pokedex-anime", "/pokedex".
     * Encaminha para `pokedex-anime.html`.
     *
     * @return caminho do recurso estático
     * @see <a href="/pokedex-anime.html">pokedex-anime.html</a>
     */
    @GetMapping({"/pokedex-anime", "/pokedex"})
    public String pokedex() {
        return "forward:/pokedex-anime.html";
    }

    /**
     * Página de login.
     *
     * Rota: "/login".
     * Encaminha para `login.html`.
     *
     * @return caminho do recurso estático
     */
    @GetMapping({"/login"})
    public String login() {
        return "forward:/login.html";
    }

    /**
     * Página de cadastro.
     *
     * Rota: "/cadastro".
     * Encaminha para `cadastro.html`.
     *
     * @return caminho do recurso estático
     */
    @GetMapping({"/cadastro"})
    public String cadastro() {
        return "forward:/cadastro.html";
    }

    /**
     * Seção Centro de Cura dentro da landing.
     *
     * Rota: "/centro".
     * Encaminha para `Pokemon.html` (seção #centro controlada no front-end).
     *
     * @return caminho do recurso estático
     */
    @GetMapping({"/centro"})
    public String centro() {
        return "forward:/Pokemon.html";
    }

    /**
     * Seção Sobre dentro da landing.
     *
     * Rota: "/sobre".
     * Encaminha para `Pokemon.html` (seção #sobre controlada no front-end).
     *
     * @return caminho do recurso estático
     */
    @GetMapping({"/sobre"})
    public String sobre() {
        return "forward:/Pokemon.html";
    }
}