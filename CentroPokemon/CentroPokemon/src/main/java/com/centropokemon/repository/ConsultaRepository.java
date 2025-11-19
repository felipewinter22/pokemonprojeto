/*
 * Centro Pokémon - Repositório de Consultas
 * ---------------------------------------
 * @file        ConsultaRepository.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.0
 * @date        2025-11-19
 * @description Interface JPA para persistência e consulta de agendamentos.
 */
package com.centropokemon.repository;

import com.centropokemon.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Integer> {
    List<Consulta> findByTreinadorIdOrderByDataHoraAsc(Integer treinadorId);
}