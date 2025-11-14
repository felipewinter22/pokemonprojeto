/*
 * Centro Pokémon - Application
 * ---------------------------------------
 * @file        CentroPokemonApplication.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-11
 * @description Classe principal, responsável pela excução do código.
 */


package com.centropokemon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CentroPokemonApplication {

    /**
     * Método principal que inicia a aplicação Spring Boot.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        SpringApplication.run(CentroPokemonApplication.class, args);
    }

}
