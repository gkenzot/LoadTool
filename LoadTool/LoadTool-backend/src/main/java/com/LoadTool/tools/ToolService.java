package com.LoadTool.tools;

import com.LoadTool.categories.Category;
import com.LoadTool.categories.CategoryNotFoundException;
import com.LoadTool.categories.CategoryRepository;
import com.LoadTool.user.User;
import com.LoadTool.user.UserNotFoundException;
import com.LoadTool.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    // Retorna todas as ferramentas não deletadas
    public List<ToolDTO> getAllTools() {
        return toolRepository.findAllNotDeleted()
                .stream()
                .map(ToolDTO::fromEntity)
                .toList();
    }

    // Busca uma ferramenta por ID, lançando exceção se não encontrada
    public ToolDTO getToolById(Long id) {
        return toolRepository.findByIdAndNotDeleted(id)
                .map(ToolDTO::fromEntity)
                .orElseThrow(() -> new ToolNotFoundException(id));
    }

    // Salva uma nova ferramenta
    public ToolDTO saveTool(Tool tool) {
        // Valida se a categoria existe
        Category category = categoryRepository.findById(tool.getCategory().getId())
                .orElseThrow(() -> new CategoryNotFoundException(tool.getCategory().getId()));

        // Valida se o proprietário existe
        User owner = userRepository.findById(tool.getOwner().getId())
                .orElseThrow(() -> new UserNotFoundException(tool.getOwner().getId()));

        // Associa a categoria e o proprietário à ferramenta
        tool.setCategory(category);
        tool.setOwner(owner);

        // Salva a ferramenta
        Tool savedTool = toolRepository.save(tool);
        return ToolDTO.fromEntity(savedTool);
    }

    // Soft delete: marca a ferramenta como deletada
    @Transactional
    public void deleteTool(Long id) {
        Tool tool = toolRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ToolNotFoundException(id));
        tool.setDeletedAt(Instant.now().toEpochMilli()); // Define a data de exclusão
        toolRepository.save(tool);
    }
}