package com.projeto.projeto.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projeto.projeto.config.security.JwtService;
import com.projeto.projeto.dto.AuthRequest;
import com.projeto.projeto.dto.AuthResponse;
import com.projeto.projeto.dto.CadastroRequest;
import com.projeto.projeto.service.AuthService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthService authService;

    
    @MockBean
    private JwtService jwtService;

    @Test
    void deveCadastrarUsuarioComSucesso() throws Exception {
        CadastroRequest request = new CadastroRequest("Novo User", "novo@email.com", "Password@123");
        AuthResponse response = new AuthResponse("novo-token-jwt");

        when(authService.cadastrar(any(CadastroRequest.class))).thenReturn(response);

        
        mockMvc.perform(post("/api/auth/cadastrar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("novo-token-jwt"));
    }

    @Test
    void deveAutenticarUsuarioComSucesso() throws Exception {
        AuthRequest request = new AuthRequest("novo@email.com", "Password@123");
        AuthResponse response = new AuthResponse("token-jwt-existente");

        when(authService.autenticar(any(AuthRequest.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/autenticar")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("token-jwt-existente"));
    }
}