package com.LoadTool.tools;

import io.swagger.v3.oas.annotations.media.Schema;
import java.math.BigDecimal;
import java.time.Instant;

public record ToolResponseDTO(
    @Schema(description = "ID da ferramenta", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    Long id,

    @Schema(description = "Nome da ferramenta", example = "Martelo de Pregos")
    String name,

    @Schema(description = "Descrição detalhada", example = "Martelo profissional com cabo de madeira")
    String description,

    @Schema(description = "ID da categoria", example = "1")
    Long category_id,

    @Schema(description = "Nome da categoria", example = "Ferramentas Manuais")
    String categoryName,

    @Schema(description = "ID do proprietário", example = "1")
    Long owner_id,

    @Schema(description = "Nome do proprietário", example = "João Silva")
    String ownerName,

    @Schema(description = "Preço diário de aluguel", example = "15.99")
    BigDecimal dailyPrice,

    @Schema(description = "Disponibilidade", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    boolean available,

    @Schema(description = "Data de criação", example = "2023-07-20T12:00:00Z", accessMode = Schema.AccessMode.READ_ONLY)
    Instant createdAt
) {
    public static ToolResponseDTO fromEntity(Tool tool) {
        return new ToolResponseDTO(
            tool.getId(),
            tool.getName(),
            tool.getDescription(),
            tool.getCategory().getId(),
            tool.getCategory().getName(),
            tool.getOwner().getId(),
            tool.getOwner().getName(),
            tool.getDailyPrice(),
            tool.getAvailable(),
            Instant.ofEpochMilli(tool.getCreatedAt())
        );
    }
}