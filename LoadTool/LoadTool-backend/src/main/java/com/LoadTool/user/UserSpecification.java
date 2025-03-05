package com.LoadTool.user;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecification {

    public static Specification<User> notDeleted() {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<User> hasEmail(String email) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("email"), email);
    }

    // Exemplo de combinação de especificações
    public static Specification<User> activeUserWithEmail(String email) {
        return Specification.where(notDeleted()).and(hasEmail(email));
    }
}