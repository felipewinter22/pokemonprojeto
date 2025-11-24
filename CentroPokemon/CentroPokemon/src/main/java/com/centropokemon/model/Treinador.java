/*
 * Centro Pokémon - Entidade Treinador
 * ---------------------------------------
 * @file        Treinador.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.3
 * @date        23/11/2025
 * @description Entidade JPA que representa o treinador cadastrado no sistema.
 *              Suporta login por usuário/e-mail e associa Pokémon do treinador
 *              para futuras consultas, vacinações e tratamentos.
 */

package com.centropokemon.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um treinador cadastrado no Centro Pokémon.
 * Inclui informações de identificação, autenticação e associação com seus Pokémon.
 */
@Entity
@Table(name = "treinadores")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Treinador {

    /** Identificador único do treinador. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /** Nome completo do treinador. */
    @Column(name = "nome", nullable = false)
    private String nome;

    /** Nome de usuário escolhido para login. */
    @Column(name = "usuario", nullable = false, unique = true)
    private String usuario;

    /** E-mail do treinador (utilizado para login e contato). */
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /** Senha do treinador. */
    @Column(name = "senha", nullable = false)
    private String senha;

    /** Telefone de contato do treinador (opcional). */
    @Column(name = "telefone")
    private String telefone;

    /** Indica se o cadastro está ativo. */
    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    /** Data/hora de criação do cadastro. */
    @Column(name = "criado_em", nullable = false)
    private LocalDateTime criadoEm;

    /** Data/hora da última atualização. */
    @Column(name = "atualizado_em", nullable = false)
    private LocalDateTime atualizadoEm;

    /** Lista de Pokémon cadastrados pelo treinador. */
    @OneToMany(mappedBy = "treinador", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference("treinador-pokemon")
    private List<Pokemon> pokemons = new ArrayList<>();

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

    /** @return id do treinador */
    public Integer getId() { return id; }
    /** @param id identificador do treinador */
    public void setId(Integer id) { this.id = id; }

    /** @return nome completo do treinador */
    public String getNome() { return nome; }
    /** @param nome nome completo do treinador */
    public void setNome(String nome) { this.nome = nome; }

    /** @return nome de usuário */
    public String getUsuario() { return usuario; }
    /** @param usuario nome de usuário */
    public void setUsuario(String usuario) { this.usuario = usuario; }

    /** @return e-mail do treinador */
    public String getEmail() { return email; }
    /** @param email e-mail do treinador */
    public void setEmail(String email) { this.email = email; }

    /** @return senha do treinador */
    public String getSenha() { return senha; }
    /** @param senha senha do treinador */
    public void setSenha(String senha) { this.senha = senha; }

    /** @return telefone de contato */
    public String getTelefone() { return telefone; }
    /** @param telefone telefone de contato */
    public void setTelefone(String telefone) { this.telefone = telefone; }

    /** @return se o cadastro está ativo */
    public Boolean getAtivo() { return ativo; }
    /** @param ativo flag de ativo/inativo */
    public void setAtivo(Boolean ativo) { this.ativo = ativo; }

    /** @return data de criação */
    public LocalDateTime getCriadoEm() { return criadoEm; }
    /** @param criadoEm data de criação */
    public void setCriadoEm(LocalDateTime criadoEm) { this.criadoEm = criadoEm; }

    /** @return data de atualização */
    public LocalDateTime getAtualizadoEm() { return atualizadoEm; }
    /** @param atualizadoEm data de atualização */
    public void setAtualizadoEm(LocalDateTime atualizadoEm) { this.atualizadoEm = atualizadoEm; }

    /** @return lista de Pokémon do treinador */
    public List<Pokemon> getPokemons() { return pokemons; }
    /** @param pokemons lista de Pokémon do treinador */
    public void setPokemons(List<Pokemon> pokemons) { this.pokemons = pokemons; }

    /**
     * Adiciona um Pokémon à coleção do treinador.
     * @param pokemon entidade Pokémon
     */
    public void adicionarPokemon(Pokemon pokemon) {
        if (pokemon == null) return;
        this.pokemons.add(pokemon);
    }

    /**
     * Remove um Pokémon da coleção do treinador.
     * @param pokemon entidade Pokémon
     */
    public void removerPokemon(Pokemon pokemon) {
        if (pokemon == null) return;
        this.pokemons.remove(pokemon);
    }
}