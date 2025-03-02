package com.LoadTool.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
    Long id,
    
    @NotBlank(message = "O nome é obrigatório")
    String name,

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    String email
) {}