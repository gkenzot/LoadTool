package com.LoadTool.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
		@Schema(accessMode = Schema.AccessMode.READ_ONLY)
	    Long id,
	    
	    @Schema(example = "Joao")
	    @NotBlank(message = "O nome é obrigatório")
	    String name,
	    
	    @Schema(example = "example@email.com")
	    @Email(message = "E-mail inválido")
	    @NotBlank(message = "O e-mail é obrigatório")
	    String email,
	    
	    @Schema(example = "5511987654321")
	    String phone,
	    
	    @Schema(example = "Rua A")
	    String address,
	    
	    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
	    Long createdAt
	) {
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