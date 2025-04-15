package com.LoadTool.users;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserResponseDTO(
    @Schema(description = "ID do usuário", example = "1")
    Long id,
    
    @Schema(description = "Nome completo", example = "João Silva")
    String name,
    
    @Schema(description = "E-mail válido", example = "joao@email.com")
    String email,
    
    // Remover no futuro
    @Schema(description = "Password", example = "senha123")
    String password,
    
    @Schema(description = "Telefone", example = "5511987654321")
    String phone,
    
    @Schema(description = "Endereço", example = "Rua A, 123")
    String address,
    
    @Schema(description = "Data de criação em milissegundos", example = "1678901234567")
    Long createdAt
) {
    public static UserResponseDTO fromEntity(User user) {
        return new UserResponseDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getPassword(), //Remover no futuro
            user.getPhone(),
            user.getAddress(),
            user.getCreatedAt()
        );
    }
}