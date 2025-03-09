package com.LoadTool.tools;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;

public record ToolDTO(
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // Ocultar no exemplo de requisição
    Long id,

    @Schema(example = "Martelo") // Exemplo para o campo "name"
    String name,

    @Schema(example = "Martelo de unha") // Exemplo para o campo "description"
    String description,

    @Schema(example = "1") // Exemplo para o campo "category_id"
    Long category_id,

    @Schema(example = "1") // Exemplo para o campo "owner_id"
    Long owner_id,

    @Schema(example = "10.50") // Exemplo para o campo "dailyPrice"
    BigDecimal dailyPrice,

    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // Ocultar no exemplo de requisição
    Boolean available,

    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // Ocultar no exemplo de requisição
    Long createdAt
) {
    public static ToolDTO fromEntity(Tool tool) {
        return new ToolDTO(
                tool.getId(),
                tool.getName(),
                tool.getDescription(),
                tool.getCategory().getId(),
                tool.getOwner().getId(),
                tool.getDailyPrice(),
                tool.getAvailable(),
                tool.getCreatedAt()
        );
    }
}