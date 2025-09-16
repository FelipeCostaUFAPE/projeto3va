package com.projeto.projeto.config;

import com.projeto.projeto.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplicationConfigTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Test
    void userDetailsServiceDeveRetornarUsuario() {
        // Cria uma instância da nossa configuração, passando o repositório "falso" (mock)
        ApplicationConfig appConfig = new ApplicationConfig(usuarioRepository);
        UserDetailsService userDetailsService = appConfig.userDetailsService();

        // Define o comportamento do mock
        when(usuarioRepository.findByEmail("user@test.com")).thenReturn(Optional.of(new com.projeto.projeto.model.Usuario()));

        // Executa e verifica
        assertNotNull(userDetailsService.loadUserByUsername("user@test.com"));
    }

    @Test
    void userDetailsServiceDeveLancarExcecao() {
        ApplicationConfig appConfig = new ApplicationConfig(usuarioRepository);
        UserDetailsService userDetailsService = appConfig.userDetailsService();

        // Define o comportamento do mock
        when(usuarioRepository.findByEmail("notfound@test.com")).thenReturn(Optional.empty());

        // Verifica se a exceção correta é lançada
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("notfound@test.com");
        });
    }
}