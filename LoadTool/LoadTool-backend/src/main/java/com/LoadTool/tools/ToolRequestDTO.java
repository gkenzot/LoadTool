package com.LoadTool.tools;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

import com.LoadTool.categories.Category;
import com.LoadTool.users.User;

public record ToolRequestDTO(
    @Schema(description = "Nome da ferramenta", example = "Martelo de Pregos")
    @NotBlank(message = "O nome da ferramenta é obrigatório")
    String name,

    @Schema(description = "Descrição detalhada", example = "Martelo profissional com cabo de madeira")
    String description,

    @Schema(description = "ID da categoria", example = "1")
    @NotNull(message = "A categoria é obrigatória")
    Long category_id,

    @Schema(description = "ID do proprietário", example = "1")
    @NotNull(message = "O proprietário é obrigatório")
    Long owner_id,

    @Schema(description = "Preço diário de aluguel", example = "15.99")
    @NotNull(message = "O preço diário é obrigatório")
    @Positive(message = "O preço deve ser positivo")
    BigDecimal dailyPrice
) {

	public Tool toEntity(Category category, User owner) {
	    Tool tool = new Tool();
	    tool.setName(name);
	    tool.setDescription(description);
	    tool.setCategory(category);
	    tool.setOwner(owner);
	    tool.setDailyPrice(dailyPrice);
	    tool.setAvailable(true);
	    tool.setCreatedAt(System.currentTimeMillis()); // ou Instant.now().toEpochMilli()
	    return tool;
	}

	public void updateEntity(Tool tool, Category category) {
	    tool.setName(name);
	    tool.setDescription(description);
	    tool.setDailyPrice(dailyPrice);
	    
	    if (!tool.getCategory().equals(category)) {
	        tool.setCategory(category);
	    }

	}

}
