/*
 * Centro Pokémon - Controlador REST Base
 * ---------------------------------------
 * @file        BaseRestController.java
 * @author      Gustavo Pigatto, Matheus Schvann, Alexandre Lampert, Mateus Stock, Felipe Winter
 * @version     1.2
 * @date        23/11/2025
 * @description Classe base abstrata para controllers REST do Centro Pokémon.
 *              Fornece configurações comuns e métodos utilitários para respostas HTTP.
 */
package com.centropokemon.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;


@RestController
@CrossOrigin(origins = "http://localhost:8080")
public abstract class BaseRestController {

    /**
     * Executa uma operação e retorna resposta de sucesso ou erro apropriado.
     * 
     * @param <T> tipo da resposta
     * @param operation operação a ser executada
     * @return ResponseEntity com resultado ou erro
     */
    protected <T> ResponseEntity<T> executeOperation(Supplier<T> operation) {
        try {
            T result = operation.get();
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Cria uma resposta de sucesso (200 OK).
     * 
     * @param <T> tipo do corpo da resposta
     * @param body corpo da resposta
     * @return ResponseEntity com status 200
     */
    protected <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }

    /**
     * Cria uma resposta de criação bem-sucedida (201 CREATED).
     * 
     * @param <T> tipo do corpo da resposta
     * @param body corpo da resposta
     * @return ResponseEntity com status 201
     */
    protected <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }

    /**
     * Cria uma resposta de não encontrado (404 NOT FOUND).
     * 
     * @param <T> tipo do corpo da resposta
     * @return ResponseEntity com status 404
     */
    protected <T> ResponseEntity<T> notFound() {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * Cria uma resposta de requisição inválida (400 BAD REQUEST).
     * 
     * @param <T> tipo do corpo da resposta
     * @return ResponseEntity com status 400
     */
    protected <T> ResponseEntity<T> badRequest() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    /**
     * Cria uma resposta de conflito (409 CONFLICT).
     * 
     * @param <T> tipo do corpo da resposta
     * @return ResponseEntity com status 409
     */
    protected <T> ResponseEntity<T> conflict() {
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    /**
     * Cria uma resposta de não autorizado (401 UNAUTHORIZED).
     * 
     * @param <T> tipo do corpo da resposta
     * @return ResponseEntity com status 401
     */
    protected <T> ResponseEntity<T> unauthorized() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    /**
     * Cria uma resposta de sem conteúdo (204 NO CONTENT).
     * 
     * @param <T> tipo do corpo da resposta
     * @return ResponseEntity com status 204
     */
    protected <T> ResponseEntity<T> noContent() {
        return ResponseEntity.noContent().build();
    }

    /**
     * Cria um mapa de resposta simples com uma chave e valor.
     * 
     * @param key chave do mapa
     * @param value valor do mapa
     * @return mapa com a chave e valor
     */
    protected Map<String, Object> mapOf(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }

    /**
     * Cria um mapa de resposta com duas chaves e valores.
     * 
     * @param key1 primeira chave
     * @param value1 primeiro valor
     * @param key2 segunda chave
     * @param value2 segundo valor
     * @return mapa com as chaves e valores
     */
    protected Map<String, Object> mapOf(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);
        return map;
    }

    /**
     * Valida se um objeto não é nulo.
     * 
     * @param obj objeto a validar
     * @param message mensagem de erro
     * @throws IllegalArgumentException se o objeto for nulo
     */
    protected void requireNonNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Valida se uma string não é nula ou vazia.
     * 
     * @param str string a validar
     * @param message mensagem de erro
     * @throws IllegalArgumentException se a string for nula ou vazia
     */
    protected void requireNonBlank(String str, String message) {
        if (str == null || str.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
