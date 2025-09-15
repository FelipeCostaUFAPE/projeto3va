package com.projeto.projeto.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CadastroRequest(
    @NotBlank(message = "O nome completo é obrigatório.")
    String nomeCompleto,
    
    @NotBlank(message = "O e-mail é obrigatório.")
    @Email(message = "O formato do e-mail é inválido.")
    String email,

    @NotBlank(message = "A senha é obrigatória.")
    @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteres.")
    // Critério: A senha deve ter no mínimo 8 caracteres e incluir letras maiúsculas, minúsculas, números e um caractere especial
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
             message = "A senha deve conter ao menos uma letra maiúscula, uma minúscula, um número e um caractere especial.")
    String senha
) {}