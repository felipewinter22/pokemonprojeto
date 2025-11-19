/*
 * Centro Pokémon - Repositório de Tipos
 * ---------------------------------------
 * @file        TipoRepository.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-17
 * @description Interface de repositório JPA para operações de persistência de Tipos.
 */

package com.centropokemon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.centropokemon.model.Tipo;

@Repository
public interface TipoRepository extends JpaRepository<Tipo, Integer> {

    /**
     * Busca um Tipo pelo nome (português) exato, ignorando maiúsculas/minúsculas.
     * @param nome nome do tipo em português
     * @return Optional com o Tipo, se encontrado
     */
    Optional<Tipo> findByNomeIgnoreCase(String nome);

    /**
     * Busca um Tipo pelo nome em português, ignorando maiúsculas/minúsculas.
     * @param nomePt nome do tipo em português
     * @return Optional com o Tipo, se encontrado
     */
    Optional<Tipo> findByNomePtIgnoreCase(String nomePt);

    /**
     * Busca um Tipo pelo nome em inglês, ignorando maiúsculas/minúsculas.
     * @param nomeEn nome do tipo em inglês
     * @return Optional com o Tipo, se encontrado
     */
    Optional<Tipo> findByNomeEnIgnoreCase(String nomeEn);

    /**
     * Alias em português para findByNomeIgnoreCase.
     * @param nome nome do tipo em português
     * @return Optional com o Tipo, se encontrado
     */
    default Optional<Tipo> buscarPorNomeIgnoreCase(String nome) {
        return findByNomeIgnoreCase(nome);
    }

    /**
     * Alias em português para findByNomePtIgnoreCase.
     * @param nomePt nome do tipo em português
     * @return Optional com o Tipo, se encontrado
     */
    default Optional<Tipo> buscarPorNomePtIgnoreCase(String nomePt) {
        return findByNomePtIgnoreCase(nomePt);
    }

    /**
     * Alias em português para findByNomeEnIgnoreCase.
     * @param nomeEn nome do tipo em inglês
     * @return Optional com o Tipo, se encontrado
     */
    default Optional<Tipo> buscarPorNomeEnIgnoreCase(String nomeEn) {
        return findByNomeEnIgnoreCase(nomeEn);
    }
}