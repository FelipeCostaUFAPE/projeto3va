package com.projeto.projeto.service;

import com.projeto.projeto.config.security.JwtService;
import com.projeto.projeto.dto.AuthRequest;
import com.projeto.projeto.dto.AuthResponse;
import com.projeto.projeto.dto.CadastroRequest;
import com.projeto.projeto.model.Usuario;
import com.projeto.projeto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse cadastrar(CadastroRequest request) {
        // Critério: O e-mail deve ser único
        if (repository.findByEmail(request.email()).isPresent()) {
            throw new IllegalArgumentException("E-mail já cadastrado.");
        }
        var usuario = new Usuario();
        usuario.setNomeCompleto(request.nomeCompleto());
        usuario.setEmail(request.email());
        // Critério: O sistema deve criptografar a senha do usuário
        usuario.setSenha(passwordEncoder.encode(request.senha()));
        
        repository.save(usuario);
        
        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken);
    }

    public AuthResponse autenticar(AuthRequest request) {
        // Critério: O sistema deve verificar se o e-mail e a senha fornecidos correspondem a uma conta existente.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.senha())
        );
        var usuario = repository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("E-mail ou senha inválidos."));
        var jwtToken = jwtService.generateToken(usuario);
        return new AuthResponse(jwtToken);
    }
}