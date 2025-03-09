package com.LoadTool.tools;

import com.LoadTool.categories.Category;
import com.LoadTool.user.User;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "tools")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tool {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "O nome da ferramenta é obrigatório")
    private String name;

    private String description;

    @NotNull(message = "A categoria é obrigatória")
    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @NotNull(message = "O proprietário é obrigatório")
    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @NotNull(message = "O preço diário é obrigatório")
    private BigDecimal dailyPrice;

    @Builder.Default
    private Boolean available = true;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "deleted_at")
    private Long deletedAt;
}

