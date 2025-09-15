package com.projeto.projeto.controller;

import com.projeto.projeto.dto.EntradaDiarioRequest;
import com.projeto.projeto.dto.EntradaDiarioResponse;
import com.projeto.projeto.service.DiarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/diario")
@RequiredArgsConstructor
public class DiarioController {

    private final DiarioService diarioService;

    @PostMapping
    public ResponseEntity<EntradaDiarioResponse> criarEntrada(@Valid @RequestBody EntradaDiarioRequest request, Principal principal) {
        EntradaDiarioResponse response = diarioService.criarEntrada(request, principal.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<EntradaDiarioResponse>> listarOuBuscarEntradas(
            Principal principal,
            @RequestParam(required = false) String palavraChave,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {

        List<EntradaDiarioResponse> response = diarioService.buscarEntradas(principal.getName(), palavraChave, data);
        return ResponseEntity.ok(response);
    }
}