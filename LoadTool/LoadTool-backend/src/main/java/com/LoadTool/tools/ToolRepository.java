package com.LoadTool.tools;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ToolRepository extends JpaRepository<Tool, Long> {

    // Busca uma ferramenta por ID, excluindo as que foram marcadas como deletadas
    @Query("SELECT t FROM Tool t WHERE t.id = :id AND t.deletedAt IS NULL")
    Optional<Tool> findByIdAndNotDeleted(@Param("id") Long id);

    // Busca todas as ferramentas não deletadas
    @Query("SELECT t FROM Tool t WHERE t.deletedAt IS NULL")
    List<Tool> findAllNotDeleted();

    // Busca ferramentas por categoria, excluindo as deletadas
    @Query("SELECT t FROM Tool t WHERE t.category.id = :categoryId AND t.deletedAt IS NULL")
    List<Tool> findByCategoryIdAndNotDeleted(@Param("categoryId") Long categoryId);

    // Busca ferramentas por proprietário, excluindo as deletadas
    @Query("SELECT t FROM Tool t WHERE t.owner.id = :ownerId AND t.deletedAt IS NULL")
    List<Tool> findByOwnerIdAndNotDeleted(@Param("ownerId") Long ownerId);
}