package com.LoadTool.categories;

import com.LoadTool.tools.ToolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final ToolRepository toolRepository; // Repositório de Tool

    // Retorna todas as categorias
    public List<CategoryDTO> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    // Busca uma categoria por ID
    public CategoryDTO getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .map(CategoryDTO::fromEntity)
                .orElseThrow(() -> new CategoryNotFoundException(id));
    }

    // Cria uma nova categoria
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setName(categoryDTO.name());

        Category savedCategory = categoryRepository.save(category);
        return CategoryDTO.fromEntity(savedCategory);
    }

    // Atualiza uma categoria existente
    @Transactional
    public CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        category.setName(categoryDTO.name());
        return CategoryDTO.fromEntity(category);
    }

    // Exclui uma categoria
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));

        // Verifica se existem ferramentas não deletadas associadas à categoria
        boolean hasActiveTools = toolRepository.existsByCategoryIdAndNotDeleted(id);
        if (hasActiveTools) {
            throw new IllegalStateException("Não é possível excluir a categoria pois existem ferramentas ativas associadas a ela.");
        }

        categoryRepository.deleteById(id);
    }
}