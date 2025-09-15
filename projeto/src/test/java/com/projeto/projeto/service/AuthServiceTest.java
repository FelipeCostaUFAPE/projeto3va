package com.projeto.projeto.service;

import com.projeto.projeto.config.security.JwtService;
import com.projeto.projeto.dto.AuthResponse;
import com.projeto.projeto.dto.CadastroRequest;
import com.projeto.projeto.model.Usuario;
import com.projeto.projeto.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    // @Mock cria uma versão "falsa" das dependências, para que possamos controlar seu comportamento
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private AuthenticationManager authenticationManager;

    // @InjectMocks cria uma instância real do AuthService, injetando os mocks acima
    @InjectMocks
    private AuthService authService;

    private CadastroRequest cadastroRequest;
    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Prepara os dados que serão usados nos testes
        cadastroRequest = new CadastroRequest("Teste User", "teste@email.com", "Password@123");
        usuario = new Usuario();
        usuario.setEmail(cadastroRequest.email());
        usuario.setNomeCompleto(cadastroRequest.nomeCompleto());
        usuario.setSenha("senhaCriptografada");
    }

    @Test
    void deveCadastrarUsuarioComSucesso() {
        // --- Arrange (Arrumar) ---
        // Define o comportamento dos mocks
        // 1. Quando o repositório procurar por um email, não deve encontrar ninguém
        when(usuarioRepository.findByEmail(cadastroRequest.email())).thenReturn(Optional.empty());
        // 2. Quando o passwordEncoder for chamado, deve retornar uma senha criptografada
        when(passwordEncoder.encode(cadastroRequest.senha())).thenReturn("senhaCriptografada");
        // 3. Quando o repositório salvar o usuário, deve retornar o usuário salvo
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        // 4. Quando o jwtService gerar um token, deve retornar um token de exemplo
        when(jwtService.generateToken(any(Usuario.class))).thenReturn("token.jwt.exemplo");

        // --- Act (Agir) ---
        // Executa o método que queremos testar
        AuthResponse response = authService.cadastrar(cadastroRequest);

        // --- Assert (Verificar) ---
        // Verifica se os resultados são os esperados
        assertNotNull(response);
        assertEquals("token.jwt.exemplo", response.token());
    }

    @Test
    void naoDeveCadastrarUsuarioComEmailDuplicado() {
        // --- Arrange ---
        // Desta vez, quando o repositório procurar pelo email, ele DEVE encontrar um usuário
        when(usuarioRepository.findByEmail(cadastroRequest.email())).thenReturn(Optional.of(usuario));

        // --- Act & Assert ---
        // Verifica se o método lança a exceção esperada quando tentamos cadastrar com email duplicado
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            authService.cadastrar(cadastroRequest);
        });

        assertEquals("E-mail já cadastrado.", exception.getMessage());
    }
}