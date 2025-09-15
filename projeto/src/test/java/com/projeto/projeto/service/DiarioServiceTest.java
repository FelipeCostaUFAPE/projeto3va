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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
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

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@email.com");
        usuario.setNomeCompleto("Teste User");

        entradaDiarioRequest = new EntradaDiarioRequest("Conteúdo de teste", 5, false);
    }

    @Test
    void deveCriarEntradaComSucesso() {
        // Arrange (Preparação)
        when(usuarioRepository.findByEmail("teste@email.com")).thenReturn(Optional.of(usuario));

        // Mock para a entidade que será salva
        EntradaDiario entradaParaSalvar = new EntradaDiario();
        entradaParaSalvar.setConteudo(entradaDiarioRequest.conteudo());
        entradaParaSalvar.setHumor(entradaDiarioRequest.humor());
        entradaParaSalvar.setUsuario(usuario);

        when(entradaDiarioRepository.save(any(EntradaDiario.class))).thenReturn(entradaParaSalvar);

        // Act (Ação)
        EntradaDiarioResponse response = diarioService.criarEntrada(entradaDiarioRequest, "teste@email.com");

        // Assert (Verificação)
        assertNotNull(response);
        assertEquals("Conteúdo de teste", response.conteudo());
        assertEquals(5, response.humor());
    }
}