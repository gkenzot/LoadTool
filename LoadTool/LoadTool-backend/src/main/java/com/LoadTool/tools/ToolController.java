package com.LoadTool.tools;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/tools")
public class ToolController {

    private final ToolService toolService;

    public ToolController(ToolService toolService) {
        this.toolService = toolService;
    }

    @GetMapping
    public ResponseEntity<List<ToolResponseDTO>> getAllTools() {
        List<ToolResponseDTO> tools = toolService.getAllTools();
        return ResponseEntity.ok(tools);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> getToolById(@PathVariable Long id) {
        ToolResponseDTO toolDTO = toolService.getToolById(id);
        return ResponseEntity.ok(toolDTO);
    }

    @PostMapping
    public ResponseEntity<ToolResponseDTO> createTool(@Valid @RequestBody ToolRequestDTO toolDTO) {
        ToolResponseDTO savedToolDTO = toolService.createTool(toolDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedToolDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ToolResponseDTO> updateTool(
            @PathVariable Long id,
            @Valid @RequestBody ToolRequestDTO updatedToolDTO
    ) {
        ToolResponseDTO savedToolDTO = toolService.updateTool(id, updatedToolDTO);
        return ResponseEntity.ok(savedToolDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTool(@PathVariable Long id) {
        toolService.deleteTool(id);
        return ResponseEntity.noContent().build();
    }
}
