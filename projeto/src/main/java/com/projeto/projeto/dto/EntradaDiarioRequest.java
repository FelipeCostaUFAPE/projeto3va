package com.projeto.projeto.dto;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

public record EntradaDiarioRequest(
    // Critério: A entrada deve ter um campo de texto para o conteúdo principal
    @Size(max = 5000, message = "O conteúdo não pode exceder 5000 caracteres.")
    String conteudo,

    // Critério: eu quero selecionar meu humor do dia em uma escala visual de 1 a 5
    @Min(value = 1, message = "O humor deve ser no mínimo 1.")
    @Max(value = 5, message = "O humor deve ser no máximo 5.")
    Integer humor,
    
    // Critério: Deve haver a opção de salvar o rascunho.
    boolean rascunho
) {}