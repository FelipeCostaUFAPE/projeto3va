package com.projeto.projeto.service;

import com.projeto.projeto.dto.EntradaDiarioRequest;
import com.projeto.projeto.dto.EntradaDiarioResponse;
import com.projeto.projeto.model.EntradaDiario;
import com.projeto.projeto.model.Usuario;
import com.projeto.projeto.repository.EntradaDiarioRepository;
import com.projeto.projeto.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiarioService {

    private final EntradaDiarioRepository entradaDiarioRepository;
    private final UsuarioRepository usuarioRepository;

    public EntradaDiarioResponse criarEntrada(EntradaDiarioRequest request, String userEmail) {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        EntradaDiario novaEntrada = new EntradaDiario();
        novaEntrada.setConteudo(request.conteudo());
        novaEntrada.setHumor(request.humor());
        novaEntrada.setRascunho(request.rascunho());
        novaEntrada.setUsuario(usuario);

        EntradaDiario entradaSalva = entradaDiarioRepository.save(novaEntrada);
        return new EntradaDiarioResponse(entradaSalva);
    }

    public List<EntradaDiarioResponse> buscarEntradas(String userEmail, String palavraChave, LocalDate data) {
        Usuario usuario = usuarioRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));

        if (palavraChave != null && !palavraChave.isEmpty()) {
            return entradaDiarioRepository.findByUsuarioIdAndConteudoContainingIgnoreCaseOrderByDataCriacaoDesc(usuario.getId(), palavraChave)
                    .stream()
                    .map(EntradaDiarioResponse::new)
                    .collect(Collectors.toList());
        }

        if (data != null) {
            return entradaDiarioRepository.findByUsuarioIdAndDataCriacaoBetweenOrderByDataCriacaoDesc(usuario.getId(), data.atStartOfDay(), data.atTime(23, 59, 59))
                    .stream()
                    .map(EntradaDiarioResponse::new)
                    .collect(Collectors.toList());
        }

        return entradaDiarioRepository.findByUsuarioIdOrderByDataCriacaoDesc(usuario.getId())
                .stream()
                .map(EntradaDiarioResponse::new)
                .collect(Collectors.toList());
    }
}