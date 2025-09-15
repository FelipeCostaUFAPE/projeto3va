package com.projeto.projeto.controller;

import com.projeto.projeto.dto.AuthRequest;
import com.projeto.projeto.dto.AuthResponse;
import com.projeto.projeto.dto.CadastroRequest;
import com.projeto.projeto.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService service;

    @PostMapping("/cadastrar")
    public ResponseEntity<AuthResponse> cadastrar(@Valid @RequestBody CadastroRequest request) {
        return ResponseEntity.ok(service.cadastrar(request));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<AuthResponse> autenticar(@RequestBody AuthRequest request) {
        return ResponseEntity.ok(service.autenticar(request));
    }
}