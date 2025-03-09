package com.LoadTool.categories;

import io.swagger.v3.oas.annotations.media.Schema;

public record CategoryDTO(
	@Schema(accessMode = Schema.AccessMode.READ_ONLY) // Ocultar no exemplo de requisição
    Long id,
    
    @Schema(example = "Elétrica")
    String name
) {
    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getName()
        );
    }
}