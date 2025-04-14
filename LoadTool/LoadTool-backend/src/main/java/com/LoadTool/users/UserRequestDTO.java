package com.LoadTool.users;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRequestDTO(
    @Schema(description = "Nome completo", example = "João Silva")
    @NotBlank(message = "O nome é obrigatório")
    String name,
    
    @Schema(description = "E-mail válido", example = "joao@email.com")
    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    String email,
    
    @Schema(description = "Senha (6-20 caracteres)", example = "senha123")
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 6, max = 20, message = "A senha deve ter entre 6 e 20 caracteres")
    String password,
    
    @Schema(description = "Telefone", example = "5511987654321")
    String phone,
    
    @Schema(description = "Endereço", example = "Rua A, 123")
    String address
) {
    // Método para converter para entidade (usado no POST)
    public User toEntity() {
        User user = new User();
        user.setName(this.name());
        user.setEmail(this.email());
        user.setPassword(this.password()); // Sem hash por enquanto
        user.setPhone(this.phone());
        user.setAddress(this.address());
        return user;
    }
    
    // Método para atualizar entidade existente (usado no PUT)
    public void updateEntity(User user) {
        if (this.name() != null) user.setName(this.name());
        if (this.email() != null) user.setEmail(this.email());
        if (this.password() != null) user.setPassword(this.password());
        if (this.phone() != null) user.setPhone(this.phone());
        if (this.address() != null) user.setAddress(this.address());
    }
}