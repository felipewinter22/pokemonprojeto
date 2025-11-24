/*
 * Centro Pokémon - Serviço de Cadastro de Pokémon do Treinador
 * ---------------------------------------
 * @file        CadastroPokemonService.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.2
 * @date        23/11/2025
 * @description Regras de domínio para adicionar, listar e remover Pokémon
 *              da coleção de um treinador. Permite que treinadores construam
 *              sua equipe de Pokémon para consultas, vacinações e tratamentos.
 */

package com.centropokemon.service;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.Treinador;
import com.centropokemon.model.Tipo;
import com.centropokemon.repository.PokemonRepository;
import com.centropokemon.repository.TreinadorRepository;
import com.centropokemon.repository.TipoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Serviço de regras para gerenciar a coleção de Pokémon do treinador.
 * Permite adicionar Pokémon pesquisados na Pokédex à coleção pessoal,
 * listar todos os Pokémon do treinador e remover Pokémon da coleção.
 * 
 * @author Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version 1.2
 * @since 1.0
 */
@Service
public class CadastroPokemonService {

    private final PokemonRepository pokemons;
    private final TreinadorRepository treinadores;
    private final TipoRepository tiposRepo;

    /**
     * Construtor com repositórios necessários.
     */
    public CadastroPokemonService(PokemonRepository pokemons, TreinadorRepository treinadores, TipoRepository tiposRepo) {
        this.pokemons = pokemons;
        this.treinadores = treinadores;
        this.tiposRepo = tiposRepo;
    }

    /**
     * Adiciona um Pokémon à coleção do treinador com validações básicas.
     * Usado principalmente para adicionar o Pokémon inicial (starter) no cadastro.
     * 
     * @param treinadorId id do treinador
     * @param pokeApiId id externo da PokeAPI (opcional)
     * @param nomePt nome em português
     * @param nomeEn nome em inglês
     * @param spriteUrl url do sprite/imagem
     * @param vidaAtual vida atual (default 100)
     * @param vidaMaxima vida máxima (default 100)
     * @return Pokémon adicionado à coleção
     * @throws IllegalArgumentException quando treinador não existe ou Pokémon já está na coleção
     */
    public Pokemon cadastrar(Integer treinadorId, Integer pokeApiId, String nomePt, String nomeEn, String spriteUrl,
                             Integer vidaAtual, Integer vidaMaxima) {
        Optional<Treinador> tOpt = treinadores.findById(treinadorId);
        if (tOpt.isEmpty()) throw new IllegalArgumentException("Treinador não encontrado: " + treinadorId);
        if (pokeApiId != null && pokemons.buscarPorTreinadorIdEPokeApiId(treinadorId, pokeApiId).isPresent()) {
            throw new IllegalArgumentException("Você já possui este Pokémon na sua coleção!");
        }
        Treinador treinador = tOpt.get();
        Pokemon p = new Pokemon();
        p.setTreinador(treinador);
        p.setPokeApiId(pokeApiId);
        p.setNomePt(nomePt);
        p.setNomeEn(nomeEn);
        p.setSpriteUrl(spriteUrl);
        p.setVidaAtual(vidaAtual != null ? vidaAtual : 100);
        p.setVidaMaxima(vidaMaxima != null ? vidaMaxima : 100);
        return pokemons.save(p);
    }

    /**
     * Adiciona um Pokémon à coleção do treinador com validações completas.
     * Usado quando o treinador pesquisa um Pokémon na Pokédex e decide adicioná-lo à sua coleção.
     * Os dados vêm pré-preenchidos da API, o treinador pode ajustar nível e habilidades.
     *
     * @param treinadorId id do treinador
     * @param pokeApiId id externo da PokeAPI (opcional, mas recomendado)
     * @param nomePt nome em português (obrigatório)
     * @param nomeEn nome em inglês (opcional, usa nomePt se não fornecido)
     * @param spriteUrl URL da imagem do Pokémon (obrigatória, deve começar com http/https)
     * @param vidaAtual vida atual do Pokémon (>=1, default 100)
     * @param vidaMaxima vida máxima do Pokémon (>=1, default 100)
     * @param nivel nível do Pokémon (>=1, default 1)
     * @param habilidades lista de habilidades do Pokémon (opcional)
     * @param tipos lista de tipos do Pokémon em pt/en (obrigatório, mínimo 1)
     * @return Pokémon adicionado à coleção do treinador
     * @throws IllegalArgumentException quando dados inválidos ou Pokémon já está na coleção
     */
    public Pokemon cadastrarCompleto(Integer treinadorId, Integer pokeApiId, String nomePt, String nomeEn, String spriteUrl,
                                     Integer vidaAtual, Integer vidaMaxima, Integer nivel, List<String> habilidades,
                                     List<String> tipos) {
        Optional<Treinador> tOpt = treinadores.findById(treinadorId);
        if (tOpt.isEmpty()) throw new IllegalArgumentException("Treinador não encontrado");
        if (pokeApiId != null && pokemons.buscarPorTreinadorIdEPokeApiId(treinadorId, pokeApiId).isPresent()) {
            throw new IllegalArgumentException("Você já possui este Pokémon na sua coleção!");
        }
        if (nomePt == null || nomePt.trim().isEmpty()) throw new IllegalArgumentException("Nome é obrigatório");
        if (spriteUrl == null || !spriteUrl.trim().toLowerCase().startsWith("http")) throw new IllegalArgumentException("Imagem inválida");
        if (tipos == null || tipos.isEmpty()) throw new IllegalArgumentException("Selecione ao menos um tipo");
        int vAtual = vidaAtual != null ? vidaAtual : 100;
        int vMax = vidaMaxima != null ? vidaMaxima : 100;
        int lvl = nivel != null ? nivel : 1;
        if (vAtual < 1 || vMax < 1 || lvl < 1) throw new IllegalArgumentException("Valores inválidos: vida/nivel");

        Treinador treinador = tOpt.get();
        Pokemon p = new Pokemon();
        p.setTreinador(treinador);
        p.setPokeApiId(pokeApiId);
        p.setNomePt(nomePt.trim());
        p.setNomeEn((nomeEn == null || nomeEn.isBlank()) ? nomePt.trim() : nomeEn.trim());
        p.setSpriteUrl(spriteUrl.trim());
        p.setVidaAtual(vAtual);
        p.setVidaMaxima(vMax);
        p.setNivel(lvl);
        p.setHabilidades(habilidades != null ? new ArrayList<>(habilidades) : new ArrayList<>());

        List<Tipo> resolved = tipos.stream()
                .map(this::resolverTipo)
                .collect(Collectors.toList());
        p.setTipos(resolved);

        return pokemons.save(p);
    }

    /**
     * Resolve um tipo de Pokémon a partir de uma string (PT ou EN).
     * Busca no banco ou cria um novo tipo se não existir.
     * 
     * @param valor nome do tipo em português ou inglês
     * @return entidade Tipo resolvida ou criada
     */
    private Tipo resolverTipo(String valor) {
        if (valor == null || valor.isBlank()) {
            Tipo t = new Tipo();
            t.setFromApiType("normal");
            return tiposRepo.save(t);
        }
        String v = valor.trim();
        return tiposRepo.findByNomePtIgnoreCase(v)
                .or(() -> tiposRepo.findByNomeEnIgnoreCase(v))
                .orElseGet(() -> {
                    Tipo t = new Tipo();
                    t.setFromApiType(v);
                    return tiposRepo.save(t);
                });
    }

    /**
     * Lista todos os Pokémon da coleção de um treinador.
     * Retorna a lista completa de Pokémon que o treinador possui,
     * incluindo o inicial e todos os adicionados via Pokédex.
     * 
     * @param treinadorId id do treinador
     * @return lista de Pokémon da coleção do treinador
     */
    public List<Pokemon> listar(Integer treinadorId) {
        return pokemons.buscarPorTreinadorId(treinadorId);
    }

    /**
     * Remove um Pokémon da coleção do treinador.
     * Só remove se o Pokémon realmente pertencer ao treinador.
     * 
     * @param treinadorId id do treinador
     * @param pokemonId id do Pokémon a ser removido
     * @return true se removido com sucesso, false se não encontrado ou não pertence ao treinador
     */
    public boolean remover(Integer treinadorId, Integer pokemonId) {
        Optional<Pokemon> pOpt = pokemons.findByIdAndTreinadorId(pokemonId, treinadorId);
        if (pOpt.isEmpty()) return false;
        pokemons.delete(pOpt.get());
        return true;
    }
}
