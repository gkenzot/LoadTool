package com.LoadTool.tools;

import com.LoadTool.categories.Category;
import com.LoadTool.categories.CategoryNotFoundException;
import com.LoadTool.categories.CategoryRepository;
import com.LoadTool.users.User;
import com.LoadTool.users.UserNotFoundException;
import com.LoadTool.users.UserRepository;
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

    public List<ToolResponseDTO> getAllTools() {
        return toolRepository.findAllNotDeleted()
                .stream()
                .map(ToolResponseDTO::fromEntity)
                .toList();
    }

    public ToolResponseDTO getToolById(Long id) {
        return toolRepository.findByIdAndNotDeleted(id)
                .map(ToolResponseDTO::fromEntity)
                .orElseThrow(() -> new ToolNotFoundException(id));
    }

    @Transactional
    public ToolResponseDTO createTool(ToolRequestDTO request) {
        Category category = categoryRepository.findById(request.category_id())
                .orElseThrow(() -> new CategoryNotFoundException(request.category_id()));

        User owner = userRepository.findByIdAndNotDeleted(request.owner_id())
                .orElseThrow(() -> new UserNotFoundException(request.owner_id()));

        Tool tool = request.toEntity(category, owner);
        tool.setCreatedAt(Instant.now().toEpochMilli());
        tool.setAvailable(true);

        Tool savedTool = toolRepository.save(tool);
        return ToolResponseDTO.fromEntity(savedTool);
    }

    @Transactional
    public ToolResponseDTO updateTool(Long id, ToolRequestDTO request) {
        Tool tool = toolRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ToolNotFoundException(id));

        Category currentCategory = tool.getCategory();
        if (!currentCategory.getId().equals(request.category_id())) {
            Category newCategory = categoryRepository.findById(request.category_id())
                    .orElseThrow(() -> new CategoryNotFoundException(request.category_id()));
            request.updateEntity(tool, newCategory);
        } else {
            request.updateEntity(tool, currentCategory);
        }

        Tool updatedTool = toolRepository.save(tool);
        return ToolResponseDTO.fromEntity(updatedTool);
    }

    @Transactional
    public void deleteTool(Long id) {
        Tool tool = toolRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new ToolNotFoundException(id));
        tool.setDeletedAt(Instant.now().toEpochMilli());
        toolRepository.save(tool);
    }

    public List<ToolResponseDTO> getToolsByCategory(Long categoryId) {
        return toolRepository.findByCategoryIdAndNotDeleted(categoryId)
                .stream()
                .map(ToolResponseDTO::fromEntity)
                .toList();
    }
}
