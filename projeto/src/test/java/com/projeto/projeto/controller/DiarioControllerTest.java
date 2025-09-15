package com.projeto.projeto.controller;

import com.projeto.projeto.config.security.JwtService;
import com.projeto.projeto.dto.EntradaDiarioRequest;
import com.projeto.projeto.dto.EntradaDiarioResponse;
import com.projeto.projeto.service.DiarioService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DiarioController.class)
public class DiarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DiarioService diarioService;

    // Esses beans são necessários para o contexto de segurança, então os mockamos também
    @MockBean
    private JwtService jwtService;

    @Test
    @WithMockUser(username = "teste@email.com") // Simula um usuário autenticado
    void deveCriarEntradaComSucesso() throws Exception {
        EntradaDiarioRequest request = new EntradaDiarioRequest("Novo post", 4, false);
        EntradaDiarioResponse response = new EntradaDiarioResponse(1L, "Novo post", LocalDateTime.now(), 4, false);

        when(diarioService.criarEntrada(any(EntradaDiarioRequest.class), eq("teste@email.com"))).thenReturn(response);

        mockMvc.perform(post("/api/diario")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"conteudo\":\"Novo post\",\"humor\":4,\"rascunho\":false}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.conteudo").value("Novo post"));
    }

    @Test
    @WithMockUser(username = "teste@email.com")
    void deveListarEntradas() throws Exception {
        when(diarioService.buscarEntradas(eq("teste@email.com"), any(), any())).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/diario"))
                .andExpect(status().isOk());
    }
}