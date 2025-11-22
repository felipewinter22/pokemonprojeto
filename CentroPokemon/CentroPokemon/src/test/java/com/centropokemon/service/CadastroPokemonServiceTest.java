package com.centropokemon.service;

import com.centropokemon.model.Pokemon;
import com.centropokemon.model.Treinador;
import com.centropokemon.model.Tipo;
import com.centropokemon.repository.PokemonRepository;
import com.centropokemon.repository.TreinadorRepository;
import com.centropokemon.repository.TipoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CadastroPokemonServiceTest {

    @Mock private PokemonRepository pokemons;
    @Mock private TreinadorRepository treinadores;
    @Mock private TipoRepository tiposRepo;

    private CadastroPokemonService service;

    @BeforeEach
    void setup() {
        service = new CadastroPokemonService(pokemons, treinadores, tiposRepo);
    }

    @Test
    @DisplayName("Cadastra completo com sucesso")
    void cadastrarCompleto_sucesso() {
        Treinador t = new Treinador();
        t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        when(pokemons.buscarPorTreinadorIdEPokeApiId(1, 25)).thenReturn(Optional.empty());
        Tipo tipo = new Tipo("Elétrico", "electric");
        when(tiposRepo.findByNomePtIgnoreCase("Elétrico")).thenReturn(Optional.of(tipo));
        when(pokemons.save(any(Pokemon.class))).thenAnswer(inv -> inv.getArgument(0));

        Pokemon p = service.cadastrarCompleto(1, 25, "Pikachu", "Pikachu", "http://img",
                100, 100, 10, List.of("Estática"), List.of("Elétrico"));

        assertNotNull(p);
        assertEquals("Pikachu", p.getNomePt());
        assertEquals(10, p.getNivel());
        assertEquals(1, p.getHabilidades().size());
    }

    @Test
    @DisplayName("Falha quando treinador não existe")
    void cadastrarCompleto_treinadorNaoExiste() {
        when(treinadores.findById(2)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(2, null, "Nome", null, "http://img",
                100, 100, 1, List.of(), List.of("Normal")));
    }

    @Test
    @DisplayName("Falha por duplicidade de pokeApiId")
    void cadastrarCompleto_duplicado() {
        Treinador t = new Treinador(); t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        when(pokemons.buscarPorTreinadorIdEPokeApiId(1, 25)).thenReturn(Optional.of(new Pokemon()));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, 25, "Pikachu", null, "http://img",
                100, 100, 1, List.of(), List.of("Elétrico")));
    }

    @Test
    @DisplayName("Validação de nome obrigatório")
    void cadastrarCompleto_nomeObrigatorio() {
        Treinador t = new Treinador(); t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, " ", null, "http://img",
                100, 100, 1, List.of(), List.of("Normal")));
    }

    @Test
    @DisplayName("Validação de imagem inválida")
    void cadastrarCompleto_imagemInvalida() {
        Treinador t = new Treinador(); t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, "Nome", null, "img",
                100, 100, 1, List.of(), List.of("Normal")));
    }

    @Test
    @DisplayName("Validação de tipos obrigatórios")
    void cadastrarCompleto_tiposObrigatorios() {
        Treinador t = new Treinador(); t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, "Nome", null, "http://img",
                100, 100, 1, List.of(), List.of()));
    }

    @Test
    @DisplayName("Validação de nível/vida inválidos")
    void cadastrarCompleto_nivelVidaInvalidos() {
        Treinador t = new Treinador(); t.setId(1);
        when(treinadores.findById(1)).thenReturn(Optional.of(t));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, "Nome", null, "http://img",
                0, 100, 1, List.of(), List.of("Normal")));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, "Nome", null, "http://img",
                100, 0, 1, List.of(), List.of("Normal")));
        assertThrows(IllegalArgumentException.class, () -> service.cadastrarCompleto(1, null, "Nome", null, "http://img",
                100, 100, 0, List.of(), List.of("Normal")));
    }
}
