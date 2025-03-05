package com.LoadTool.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
    Long id,
    
    @NotBlank(message = "O nome é obrigatório")
    String name,

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    String email,

    String phone,
    String address,
    Long createdAt
) {
    // Método estático para converter de User para UserDTO
    public static UserDTO fromEntity(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getCreatedAt()
        );
    }
}