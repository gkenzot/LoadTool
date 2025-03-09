package com.LoadTool.tools;

import org.springframework.data.jpa.domain.Specification;

public class ToolSpecification {

    // Filtra ferramentas não deletadas
    public static Specification<Tool> notDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    // Filtra ferramentas por nome
    public static Specification<Tool> hasName(String name) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(root.get("name"), "%" + name + "%");
    }

    // Filtra ferramentas por categoria
    public static Specification<Tool> hasCategory(Long categoryId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category").get("id"), categoryId);
    }

    // Filtra ferramentas por disponibilidade
    public static Specification<Tool> isAvailable(Boolean available) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("available"), available);
    }

    // Combina múltiplas especificações
    public static Specification<Tool> filterTools(String name, Long categoryId, Boolean available) {
        return Specification.where(notDeleted())
                .and(hasName(name))
                .and(hasCategory(categoryId))
                .and(isAvailable(available));
    }
}