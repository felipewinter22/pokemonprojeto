/*
 * Centro Pokémon - Classe base Pokémon
 * ---------------------------------------
 * @file        Pokemon.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-11
 * @description Classe base representando um Pokémon para uso com a API.
 */

package com.centropokemon.model;

import java.util.List;

/**
 * Representa um Pokémon dentro do sistema do Centro Pokémon.
 * Contém informações básicas como nome, tipos, vida atual,
 * sprite e descrição. Também inclui utilitários de cura.
 */
public class Pokemon {

    /** Identificador do ID do Pokémon. */
    private Integer id;

    /** Nome do Pokémon. */
    private String nome;

    /** Lista de tipos do Pokémon */
    private List<String> tipos;

    /** URL ou caminho para a imagem do Pokémon. */
    private String sprite;

    /** Descrição do Pokémon. */
    private String descricao;

    /** Vida atual do Pokémon. */
    private Integer vida;

    /** Vida máxima do Pokémon. */
    private Integer vidaMaxima;

    /**
     * Construtor padrão. Cria um Pokémon com vida inicial e máxima iguais a 100.
     */
    public Pokemon() {
        this.vida = 100;
        this.vidaMaxima = 100;
    }

    /**
     * Construtor completo para inicialização com valores básicos.
     *
     * @param id       ID do Pokémon
     * @param nome     Nome do Pokémon
     * @param tipos    Lista contendo os tipos do Pokémon
     * @param sprite   URL ou caminho do sprite
     */
    public Pokemon(Integer id, String nome, List<String> tipos, String sprite) {
        this();
        this.id = id;
        this.nome = nome;
        this.tipos = tipos;
        this.sprite = sprite;
    }

    /** @return o ID do Pokémon */
    public Integer getId() {
        return id;
    }

    /**
     * Define o ID do Pokémon.
     *
     * @param id novo ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return o nome do Pokémon */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome do Pokémon.
     *
     * @param nome nome a ser definido
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /** @return lista dos tipos do Pokémon */
    public List<String> getTipos() {
        return tipos;
    }

    /**
     * Define a lista de tipos do Pokémon.
     *
     * @param tipos tipos a serem definidos
     */
    public void setTipos(List<String> tipos) {
        this.tipos = tipos;
    }

    /** @return sprite/imagem do Pokémon */
    public String getSprite() {
        return sprite;
    }

    /**
     * Define o sprite do Pokémon.
     *
     * @param sprite URL ou caminho da imagem
     */
    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    /** @return descrição do Pokémon */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Define a descrição do Pokémon.
     *
     * @param descricao texto descritivo
     */
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    /** @return vida atual do Pokémon */
    public Integer getVida() {
        return vida;
    }

    /**
     * Define a vida atual do Pokémon.
     *
     * @param vida quantidade atual de vida
     */
    public void setVida(Integer vida) {
        this.vida = vida;
    }

    /** @return vida máxima do Pokémon */
    public Integer getVidaMaxima() {
        return vidaMaxima;
    }

    /**
     * Define a vida máxima do Pokémon.
     *
     * @param vidaMaxima quantidade máxima de vida
     */
    public void setVidaMaxima(Integer vidaMaxima) {
        this.vidaMaxima = vidaMaxima;
    }

    /**
     * Restaura a vida do Pokémon para o valor máximo.
     */
    public void tratar() {
        this.vida = this.vidaMaxima;
    }

    /**
     * Verifica se o Pokémon precisa ser curado.
     *
     * @return true se vida < vidaMaxima, false caso contrário
     */
    public boolean precisaCurar() {
        return vida < vidaMaxima;
    }
}
