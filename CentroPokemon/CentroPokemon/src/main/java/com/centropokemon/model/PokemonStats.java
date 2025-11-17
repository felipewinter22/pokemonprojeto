/*
 * Centro Pokémon - Classe base Status
 * ---------------------------------------
 * @file        PokemonStats.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-16
 * @description Entidade JPA que representa os atributos de combate de um Pokémon.
 * Contém HP, Ataque, Defesa, Velocidade, Ataque Especial e Defesa Especial.
 */

package com.centropokemon.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Representa os atributos de combate de um Pokémon.
 * Contém HP, Ataque, Defesa, Velocidade, Ataque Especial e Defesa Especial.
 */
@Entity
@Table(name = "pokemon_stats")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class PokemonStats {

    /** Identificador do registro de atributos do Pokémon. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Referência ao Pokémon dono destes atributos. */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false, unique = true)
    @JsonBackReference("stats-pokemon")
    private Pokemon pokemon;

    /** Pontos de vida base (HP). */
    @Column(name = "hp", nullable = false)
    private Integer hp;

    /** Valor de ataque base. */
    @Column(name = "ataque", nullable = false)
    private Integer ataque;

    /** Valor de defesa base. */
    @Column(name = "defesa", nullable = false)
    private Integer defesa;

    /** Valor de velocidade base. */
    @Column(name = "velocidade", nullable = false)
    private Integer velocidade;

    /** Valor de ataque especial base. */
    @Column(name = "ataque_especial", nullable = false)
    private Integer ataqueEspecial;

    /** Valor de defesa especial base. */
    @Column(name = "defesa_especial", nullable = false)
    private Integer defesaEspecial;

    /** Construtor padrão. */
    public PokemonStats() {}

    /**
     * Construtor completo para inicialização com valores de atributos.
     *
     * @param pokemon         Pokémon associado
     * @param hp              HP base
     * @param ataque          Ataque base
     * @param defesa          Defesa base
     * @param velocidade      Velocidade base
     * @param ataqueEspecial  Ataque especial base
     * @param defesaEspecial  Defesa especial base
     */
    public PokemonStats(Pokemon pokemon, Integer hp, Integer ataque, Integer defesa, 
                       Integer velocidade, Integer ataqueEspecial, Integer defesaEspecial) {
        this.pokemon = pokemon;
        this.hp = hp;
        this.ataque = ataque;
        this.defesa = defesa;
        this.velocidade = velocidade;
        this.ataqueEspecial = ataqueEspecial;
        this.defesaEspecial = defesaEspecial;
    }

    /** @return identificador único do registro de stats */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador do registro de stats.
     *
     * @param id novo identificador
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /** @return Pokémon associado a estes atributos */
    public Pokemon getPokemon() {
        return pokemon;
    }

    /**
     * Define o Pokémon associado a estes atributos.
     *
     * @param pokemon Pokémon dono destes stats
     */
    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    /** @return valor de HP */
    public Integer getHp() {
        return hp;
    }

    /**
     * Define o valor de HP.
     *
     * @param hp novo HP
     */
    public void setHp(Integer hp) {
        this.hp = hp;
    }

    /** @return valor de ataque */
    public Integer getAtaque() {
        return ataque;
    }

    /**
     * Define o valor de ataque.
     *
     * @param ataque novo ataque
     */
    public void setAtaque(Integer ataque) {
        this.ataque = ataque;
    }

    /** @return valor de defesa */
    public Integer getDefesa() {
        return defesa;
    }

    /**
     * Define o valor de defesa.
     *
     * @param defesa nova defesa
     */
    public void setDefesa(Integer defesa) {
        this.defesa = defesa;
    }

    /** @return valor de velocidade */
    public Integer getVelocidade() {
        return velocidade;
    }

    /**
     * Define o valor de velocidade.
     *
     * @param velocidade nova velocidade
     */
    public void setVelocidade(Integer velocidade) {
        this.velocidade = velocidade;
    }

    /** @return valor de ataque especial */
    public Integer getAtaqueEspecial() {
        return ataqueEspecial;
    }

    /**
     * Define o valor de ataque especial.
     *
     * @param ataqueEspecial novo ataque especial
     */
    public void setAtaqueEspecial(Integer ataqueEspecial) {
        this.ataqueEspecial = ataqueEspecial;
    }

    /** @return valor de defesa especial */
    public Integer getDefesaEspecial() {
        return defesaEspecial;
    }

    /**
     * Define o valor de defesa especial.
     *
     * @param defesaEspecial nova defesa especial
     */
    public void setDefesaEspecial(Integer defesaEspecial) {
        this.defesaEspecial = defesaEspecial;
    }
}