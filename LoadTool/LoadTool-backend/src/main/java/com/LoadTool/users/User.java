package com.LoadTool.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY) // Marca o ID como somente leitura
    private Long id;

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "O e-mail é obrigatório")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "A senha é obrigatória")
    private String password;

    private String phone;
    private String address;

    @Column(name = "created_at")
    // @CreatedDate
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long createdAt;
    
    // @Column(name = "created_at")
    // @LastModifiedDate
    // @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    // private Long updatedAt;

    @Column(name = "deleted_at")
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long deletedAt;
}