/*
 * Centro Pokémon - Repositório de Treinadores
 * ---------------------------------------
 * @file        TreinadorRepository.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-18
 * @description Interface de repositório JPA para operações de persistência de Treinadores.
 */

package com.centropokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centropokemon.model.Treinador;

/**
 * Repositório JPA para a entidade {@link Treinador}.
 * Fornece consultas por e-mail, usuário e nome.
 */
@Repository
public interface TreinadorRepository extends JpaRepository<Treinador, Integer> {

    /**
     * Busca um treinador pelo e-mail, ignorando maiúsculas/minúsculas.
     * @param email e-mail do treinador
     * @return Optional com o treinador, se encontrado
     */
    Optional<Treinador> findByEmailIgnoreCase(String email);

    /**
     * Busca um treinador pelo nome de usuário, ignorando maiúsculas/minúsculas.
     * @param usuario nome de usuário
     * @return Optional com o treinador, se encontrado
     */
    Optional<Treinador> findByUsuarioIgnoreCase(String usuario);

    /**
     * Busca um treinador pelo nome, ignorando maiúsculas/minúsculas.
     * @param nome nome completo do treinador
     * @return Optional com o treinador, se encontrado
     */
    Optional<Treinador> findByNomeIgnoreCase(String nome);

    /**
     * Verifica se existe um treinador cadastrado com o e-mail informado.
     * @param email e-mail do treinador
     * @return true se existir, false caso contrário
     */
    boolean existsByEmailIgnoreCase(String email);

    /**
     * Verifica se existe um treinador cadastrado com o usuário informado.
     * @param usuario nome de usuário
     * @return true se existir, false caso contrário
     */
    boolean existsByUsuarioIgnoreCase(String usuario);

    /**
     * Alias em português para {@link #findByEmailIgnoreCase(String)}.
     * @param email e-mail do treinador
     * @return Optional com o treinador, se encontrado
     */
    default Optional<Treinador> buscarPorEmail(String email) {
        return findByEmailIgnoreCase(email);
    }

    /**
     * Alias em português para {@link #findByUsuarioIgnoreCase(String)}.
     * @param usuario nome de usuário
     * @return Optional com o treinador, se encontrado
     */
    default Optional<Treinador> buscarPorUsuario(String usuario) {
        return findByUsuarioIgnoreCase(usuario);
    }

    /**
     * Alias em português para {@link #findByNomeIgnoreCase(String)}.
     * @param nome nome completo do treinador
     * @return Optional com o treinador, se encontrado
     */
    default Optional<Treinador> buscarPorNome(String nome) {
        return findByNomeIgnoreCase(nome);
    }

    /**
     * Alias em português para {@link #existsByEmailIgnoreCase(String)}.
     * @param email e-mail do treinador
     * @return true se existir, false caso contrário
     */
    default boolean existePorEmail(String email) {
        return existsByEmailIgnoreCase(email);
    }

    /**
     * Alias em português para {@link #existsByUsuarioIgnoreCase(String)}.
     * @param usuario nome de usuário
     * @return true se existir, false caso contrário
     */
    default boolean existePorUsuario(String usuario) {
        return existsByUsuarioIgnoreCase(usuario);
    }
}