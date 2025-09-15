package com.projeto.projeto.dto;

import com.projeto.projeto.model.EntradaDiario;

import java.time.LocalDateTime;

public record EntradaDiarioResponse(
        Long id,
        String conteudo,
        LocalDateTime dataCriacao,
        Integer humor,
        boolean rascunho
) {
    public EntradaDiarioResponse(EntradaDiario entrada) {
        this(
            entrada.getId(),
            entrada.getConteudo(),
            entrada.getDataCriacao(),
            entrada.getHumor(),
            entrada.isRascunho()
        );
    }
}