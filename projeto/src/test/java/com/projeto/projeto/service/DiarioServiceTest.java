package com.projeto.projeto.service;

import com.projeto.projeto.dto.EntradaDiarioRequest;
import com.projeto.projeto.dto.EntradaDiarioResponse;
import com.projeto.projeto.model.EntradaDiario;
import com.projeto.projeto.model.Usuario;
import com.projeto.projeto.repository.EntradaDiarioRepository;
import com.projeto.projeto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DiarioServiceTest {

    @Mock
    private EntradaDiarioRepository entradaDiarioRepository;
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private DiarioService diarioService;

    private Usuario usuario;
    private EntradaDiarioRequest entradaDiarioRequest;
    private EntradaDiario entradaDiario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@email.com");
        usuario.setNomeCompleto("Teste User");

        entradaDiarioRequest = new EntradaDiarioRequest("Conteúdo de teste", 5, false);

        entradaDiario = new EntradaDiario();
        entradaDiario.setId(1L);
        entradaDiario.setConteudo("Conteúdo de teste");
        entradaDiario.setHumor(5);
        entradaDiario.setUsuario(usuario);
    }

    @Test
    void deveCriarEntradaComSucesso() {
        // Arrange
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(entradaDiarioRepository.save(any(EntradaDiario.class))).thenReturn(entradaDiario);

        // Act
        EntradaDiarioResponse response = diarioService.criarEntrada(entradaDiarioRequest, "teste@email.com");

        // Assert
        assertNotNull(response);
        assertEquals("Conteúdo de teste", response.conteudo());
        assertEquals(5, response.humor());
    }

    @Test
    void deveBuscarEntradasPorPalavraChave() {
        // Arrange
        String palavraChave = "teste";
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(entradaDiarioRepository.findByUsuarioIdAndConteudoContainingIgnoreCaseOrderByDataCriacaoDesc(usuario.getId(), palavraChave))
            .thenReturn(Collections.singletonList(entradaDiario));

        // Act
        List<EntradaDiarioResponse> response = diarioService.buscarEntradas("teste@email.com", palavraChave, null);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }

    @Test
    void deveListarTodasAsEntradas() {
        // Arrange
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));
        when(entradaDiarioRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuario.getId()))
            .thenReturn(Collections.singletonList(entradaDiario));

        // Act
        List<EntradaDiarioResponse> response = diarioService.buscarEntradas("teste@email.com", null, null);

        // Assert
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(1, response.size());
    }
}