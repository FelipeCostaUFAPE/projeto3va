package com.projeto.projeto.config;

import com.projeto.projeto.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = ApplicationConfig.class)
public class ApplicationConfigTest {

    @Autowired
    private ApplicationContext context;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Test
    void deveCarregarOsBeansDeConfiguracao() {
        // Verifica se os beans definidos em ApplicationConfig foram criados com sucesso
        assertThat(context.getBean(UserDetailsService.class)).isNotNull();
        assertThat(context.getBean(AuthenticationProvider.class)).isNotNull();
        assertThat(context.getBean(PasswordEncoder.class)).isNotNull();
        // O AuthenticationManager é um caso especial e não é verificado desta forma
    }
}