package com.LoadTool.tools;

import com.LoadTool.categories.Category;
import com.LoadTool.categories.CategoryNotFoundException;
import com.LoadTool.categories.CategoryRepository;
import com.LoadTool.users.User;
import com.LoadTool.users.UserNotFoundException;
import com.LoadTool.users.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final ToolService toolService;
    private final CategoryRepository categoryRepository; // Injetado
    private final UserRepository userRepository; // Injetado

    // Construtor para injeção de dependência
    public ToolController(
            ToolService toolService,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.toolService = toolService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<List<ToolDTO>> getAllTools() {
        List<ToolDTO> tools = toolService.getAllTools();
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> getToolById(@PathVariable Long id) {
        ToolDTO toolDTO = toolService.getToolById(id);
        return ResponseEntity.ok(toolDTO);
    }

    @PostMapping
    public ResponseEntity<ToolDTO> createTool(@Valid @RequestBody ToolDTO toolDTO) {
        Tool tool = new Tool();
        tool.setName(toolDTO.name());
        tool.setDescription(toolDTO.description());
        tool.setDailyPrice(toolDTO.dailyPrice());
        tool.setCreatedAt(Instant.now().toEpochMilli());

        // Busca a categoria e o proprietário pelos IDs fornecidos
        Category category = categoryRepository.findById(toolDTO.category_id())
                .orElseThrow(() -> new CategoryNotFoundException(toolDTO.category_id()));
        User owner = userRepository.findById(toolDTO.owner_id())
                .orElseThrow(() -> new UserNotFoundException(toolDTO.owner_id()));

        tool.setCategory(category);
        tool.setOwner(owner);

        // O ID será gerado automaticamente pelo banco de dados
        ToolDTO savedToolDTO = toolService.saveTool(tool);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedToolDTO);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<ToolDTO> updateTool(
            @PathVariable Long id,
            @Valid @RequestBody ToolDTO updatedToolDTO
    ) {
        // Converte o DTO para a entidade Tool
        Tool updatedTool = new Tool();
        updatedTool.setName(updatedToolDTO.name());
        updatedTool.setDescription(updatedToolDTO.description());
        updatedTool.setDailyPrice(updatedToolDTO.dailyPrice());

        // Define a categoria e o proprietário, se fornecidos
        if (updatedToolDTO.category_id() != null) {
            Category category = categoryRepository.findById(updatedToolDTO.category_id())
                    .orElseThrow(() -> new CategoryNotFoundException(updatedToolDTO.category_id()));
            updatedTool.setCategory(category);
        }

//        if (updatedToolDTO.owner_id() != null) {
//            User owner = userRepository.findById(updatedToolDTO.owner_id())
//                    .orElseThrow(() -> new UserNotFoundException(updatedToolDTO.owner_id()));
//            updatedTool.setOwner(owner);
//        }

        // Chama o serviço para atualizar a ferramenta
        ToolDTO savedToolDTO = toolService.updateTool(id, updatedTool);
        return ResponseEntity.ok(savedToolDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.noContent().build();
    }
}