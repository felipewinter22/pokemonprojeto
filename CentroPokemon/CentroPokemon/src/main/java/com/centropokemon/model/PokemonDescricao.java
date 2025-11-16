/*
 * Centro Pokémon - Classe base Descrição
 * ---------------------------------------
 * @file        PokemonDescricao.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-16
 * @description Classe base representando a descrição do Pokémon puxada da API.
 */

/**
 * Entidade JPA que representa as descrições de um Pokémon em diferentes idiomas.
 * Armazena as descrições em português e inglês para suportar tradução.
 */

package com.centropokemon.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pokemon_descricoes")
public class PokemonDescricao {

    /** Identificador do registro de descrição. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Referência ao Pokémon ao qual a descrição pertence. */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    /** Descrição em português. */
    @Column(name = "descricao_pt", columnDefinition = "TEXT")
    private String descricaoPt;

    /** Descrição em inglês. */
    @Column(name = "descricao_en", columnDefinition = "TEXT")
    private String descricaoEn;

    /** Construtor padrão. */
    public PokemonDescricao() {}

    /**
     * Construtor completo para inicialização das descrições.
     *
     * @param pokemon     Pokémon associado
     * @param descricaoPt Descrição em português
     * @param descricaoEn Descrição em inglês
     */
    public PokemonDescricao(Pokemon pokemon, String descricaoPt, String descricaoEn) {
        this.pokemon = pokemon;
        this.descricaoPt = descricaoPt;
        this.descricaoEn = descricaoEn;
    }

    /** @return identificador único da descrição */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador da descrição.
     *
     * @param id novo identificador
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return Pokémon associado à descrição */
    public Pokemon getPokemon() {
        return pokemon;
    }

    /**
     * Define o Pokémon associado à descrição.
     *
     * @param pokemon Pokémon ao qual a descrição pertence
     */
    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    /** @return descrição em português */
    public String getDescricaoPt() {
        return descricaoPt;
    }

    /**
     * Define a descrição em português.
     *
     * @param descricaoPt nova descrição em português
     */
    public void setDescricaoPt(String descricaoPt) {
        this.descricaoPt = descricaoPt;
    }

    /** @return descrição em inglês */
    public String getDescricaoEn() {
        return descricaoEn;
    }

    /**
     * Define a descrição em inglês.
     *
     * @param descricaoEn nova descrição em inglês
     */
    public void setDescricaoEn(String descricaoEn) {
        this.descricaoEn = descricaoEn;
    }
}