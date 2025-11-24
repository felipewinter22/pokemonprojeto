/*
 * Centro Pokémon - Entidade Consulta
 * ---------------------------------------
 * @file        Consulta.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.1
 * @date        23/11/2025
 * @description Entidade JPA que representa uma consulta médica agendada
 *              para um Pokémon de um treinador. Inclui tipo de consulta,
 *              data/hora e observações.
 */
package com.centropokemon.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "treinador_id", nullable = false)
    private Treinador treinador;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    @Column(name = "tipo", nullable = false, length = 32)
    private String tipo;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    /** Data/hora de criação do registro. */
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    /** Data/hora da última atualização do registro. */
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    /**
     * Inicializa timestamps de criação e atualização antes de persistir.
     */
    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.criadoEm = now;
        this.atualizadoEm = now;
    }

    /**
     * Atualiza timestamp de modificação antes de atualizar.
     */
    @PreUpdate
    protected void onUpdate() {
        this.atualizadoEm = LocalDateTime.now();
    }



    /** @return identificador da consulta */
    public Integer getId() { return id; }
    /** @param id identificador da consulta */
    public void setId(Integer id) { this.id = id; }

    /** @return treinador associado à consulta */
    public Treinador getTreinador() { return treinador; }
    /** @param treinador treinador associado à consulta */
    public void setTreinador(Treinador treinador) { this.treinador = treinador; }

    /** @return Pokémon associado à consulta */
    public Pokemon getPokemon() { return pokemon; }
    /** @param pokemon Pokémon associado à consulta */
    public void setPokemon(Pokemon pokemon) { this.pokemon = pokemon; }

    /** @return tipo da consulta */
    public String getTipo() { return tipo; }
    /** @param tipo tipo da consulta */
    public void setTipo(String tipo) { this.tipo = tipo; }

    /** @return data e hora da consulta */
    public LocalDateTime getDataHora() { return dataHora; }
    /** @param dataHora data e hora da consulta */
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    /** @return observações da consulta */
    public String getObservacoes() { return observacoes; }
    /** @param observacoes observações da consulta */
    public void setObservacoes(String observacoes) { this.observacoes = observacoes; }

    /** @return data de criação do registro */
    public LocalDateTime getCriadoEm() { return criadoEm; }
    /** @param criadoEm data de criação do registro */
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    /** @return data da última atualização */
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    /** @param atualizadoEm data da última atualização */
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }
}