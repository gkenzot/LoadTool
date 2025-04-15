package com.LoadTool;

import com.LoadTool.categories.CategoryDTO;
import com.LoadTool.categories.CategoryService;
import com.LoadTool.tools.ToolRequestDTO;
import com.LoadTool.tools.ToolService;
import com.LoadTool.users.UserRequestDTO;
import com.LoadTool.users.UserResponseDTO;
import com.LoadTool.users.UserService;

import jakarta.validation.Valid;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/")
public class LoadToolController {

	private final UserService userService;
	private final ToolService toolService;
	private final CategoryService categoryService;

	public LoadToolController(UserService userService, ToolService toolService, CategoryService categoryService) {
		this.userService = userService;
		this.toolService = toolService;
		this.categoryService = categoryService;
	}

	// --- Página Inicial ---
	@GetMapping
	public String home(Model model) {
		//model.addAttribute("recentTools", toolService.getAllTools().stream().limit(5).toList());
		//model.addAttribute("popularCategories", categoryService.getAllCategories().stream().limit(3).toList());

		return "index";
	}
	
	// --- Users ---
	@GetMapping("/users")
	public String listUsers(Model model) {
		model.addAttribute("users", userService.getAllUsers());
		return "users/list";
	}

	@GetMapping("/users/view/{id}")
	public String viewUser(@PathVariable Long id, Model model) {
		model.addAttribute("user", userService.getUserById(id));
		return "users/view";
	}
	
	@GetMapping("/users/new")
	public String newUserForm(Model model) {
	    model.addAttribute("user", new UserRequestDTO("", "", "", "", ""));
	    return "users/form";
	}
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable Long id, Model model) {
	    UserResponseDTO userResponse = userService.getUserById(id); // retorna um UserResponseDTO seguro

	    UserRequestDTO userDTO = new UserRequestDTO(
	        userResponse.name(),
	        userResponse.email(),
	        userResponse.password(), //remover no futuro
	        userResponse.phone(),
	        userResponse.address()
	    );

	    model.addAttribute("user", userDTO);
	    model.addAttribute("userId", userResponse.id()); // só o ID extra, para uso no form
	    return "users/form";
	}

	@PostMapping("/users/new")
	public String createUser(@Valid @ModelAttribute("user") UserRequestDTO userDTO,
	                         @RequestParam(required = false) Long userId,
	                         BindingResult result,
	                         Model model) {
	    if (result.hasErrors()) {
	        return "users/form";
	    }

	    if (userId != null) {
	        userService.updateUser(userId, userDTO);
	    } else {
	        userService.createUser(userDTO);
	    }

	    return "redirect:/users";
	}

	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable Long id) {
	    userService.deleteUser(id);
	    return "redirect:/users"; // Redireciona de volta para a lista de usuários após a exclusão
	}

	
	// --- Tools ---
	@GetMapping("/tools")
	public String listTools(Model model) {
		model.addAttribute("tools", toolService.getAllTools());
		return "tools/list";
	}

	@GetMapping("/tools/new")
	public String newToolForm(Model model) {
	    model.addAttribute("tool", new ToolRequestDTO("", "", null, null, BigDecimal.ZERO));
	    model.addAttribute("categories", categoryService.getAllCategories());
	    model.addAttribute("users", userService.getAllUsers());
	    return "tools/form";
	}


	@GetMapping("/tools/view/{id}")
	public String viewTool(@PathVariable Long id, Model model) {
		model.addAttribute("tool", toolService.getToolById(id));
		return "tools/view";
	}

	@GetMapping("/tools/edit/{id}")
	public String editTool(@PathVariable Long id, Model model) {
		model.addAttribute("tool", toolService.getToolById(id));
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("users", userService.getAllUsers());
		return "tools/form";
	}
	
	@PostMapping("/tools/new")
	public String createTool(@Valid @ModelAttribute("tool") ToolRequestDTO toolDTO,
	                         @RequestParam(required = false) Long toolId,
	                         BindingResult result,
	                         Model model) {
	    if (result.hasErrors()) {
	        // Se houver erros, retorne o formulário de tools com os erros
	        model.addAttribute("categories", categoryService.getAllCategories());
	        model.addAttribute("users", userService.getAllUsers());
	        return "tools/form";
	    }

	    if (toolId != null) {
	        // Se houver um ID de ferramenta, atualize a ferramenta existente
	        toolService.updateTool(toolId, toolDTO);
	    } else {
	        // Caso contrário, crie uma nova ferramenta
	        toolService.createTool(toolDTO);
	    }

	    // Redireciona para a lista de ferramentas após a criação/atualização
	    return "redirect:/tools";
	}
	
	@GetMapping("/tools/delete/{id}")
	public String deleteTool(@PathVariable Long id) {
	    toolService.deleteTool(id);
	    return "redirect:/tools";
	}

	
	// --- Categories ---
	@GetMapping("/categories")
	public String listCategories(
	        @RequestParam(required = false) Long editId,
	        Model model) {
	    
	    model.addAttribute("categories", categoryService.getAllCategories());

	    if (editId != null) {
	        model.addAttribute("category", categoryService.getCategoryById(editId));
	    } else {
	        model.addAttribute("category", new CategoryDTO(null, ""));
	    }

	    return "categories/list";
	}

	@GetMapping("/categories/new")
	public String showCategoryForm(Model model) {
	    model.addAttribute("category", new CategoryDTO(null, ""));
	    return "categories/form";
	}

	@PostMapping("/categories")
	public String createOrUpdateCategory(
	        @Valid @ModelAttribute("category") CategoryDTO categoryDTO,
	        BindingResult result,
	        Model model) {
	    
	    if (result.hasErrors()) {
	        model.addAttribute("categories", categoryService.getAllCategories());
	        return "categories/list";
	    }

	    if (categoryDTO.id() != null) {
	        categoryService.updateCategory(categoryDTO.id(), categoryDTO);
	    } else {
	        categoryService.createCategory(categoryDTO);
	    }

	    return "redirect:/categories";
	}

	
	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable Long id, Model model) {
		model.addAttribute("category", categoryService.getCategoryById(id));
		return "categories/form";
	}

	@GetMapping("/categories/view/{id}")
	public String viewCategory(@PathVariable Long id, Model model) {
		CategoryDTO category = categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		model.addAttribute("tools", toolService.getAllTools().stream()
				.filter(t -> t.category_id() != null && t.category_id().equals(id)).toList());
		return "categories/view";
	}
	
	@GetMapping("/categories/delete/{id}")
	public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
	    try {
	        categoryService.deleteCategory(id);
	        redirectAttributes.addFlashAttribute("success", "Categoria excluída com sucesso!");
	    } catch (RuntimeException ex) {
	        redirectAttributes.addFlashAttribute("error", ex.getMessage());
	    }
	    return "redirect:/categories";
	}

}