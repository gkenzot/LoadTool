package com.LoadTool.categories;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CategoryDTO(
	@Schema(accessMode = Schema.AccessMode.READ_ONLY) // Ocultar no exemplo de requisição
    Long id,
    
    @Schema(example = "Elétrica")
	@NotBlank(message = "O nome da categoria é obrigatório")
	String name

) {
    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}