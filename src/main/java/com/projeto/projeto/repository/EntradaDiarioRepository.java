package com.projeto.projeto.repository;

import com.projeto.projeto.model.EntradaDiario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntradaDiarioRepository extends JpaRepository<EntradaDiario, Long> {

    // Busca por usuário, ordenado pela data de criação
    List<EntradaDiario> findByUsuarioIdOrderByDataCriacaoDesc(Long usuarioId);

    // Busca por palavra-chave no conteúdo
    List<EntradaDiario> findByUsuarioIdAndConteudoContainingIgnoreCaseOrderByDataCriacaoDesc(Long usuarioId, String palavraChave);

    // Busca por intervalo de datas
    List<EntradaDiario> findByUsuarioIdAndDataCriacaoBetweenOrderByDataCriacaoDesc(Long usuarioId, LocalDateTime inicio, LocalDateTime fim);
}