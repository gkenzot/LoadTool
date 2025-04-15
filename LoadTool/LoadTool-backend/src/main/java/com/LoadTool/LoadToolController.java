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
	public String showToolForm(Model model) {
		// Cria um novo ToolRequestDTO com valores padrão
		ToolRequestDTO toolRequestDTO = new ToolRequestDTO("", // name
				"", // description
				null, // category_id
				null, // owner_id
				BigDecimal.ZERO // dailyPrice
		);

		// Adiciona o DTO ao modelo para ser utilizado no formulário
		model.addAttribute("tool", toolRequestDTO);

		// Adiciona as listas de categorias e usuários ao modelo
		model.addAttribute("categories", categoryService.getAllCategories());
		model.addAttribute("users", userService.getAllUsers());

		return "tools/form";
	}

	@GetMapping("/tools/{id}")
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

	// --- Categories ---
	@GetMapping("/categories")
	public String listCategories(Model model) {
		model.addAttribute("categories", categoryService.getAllCategories());
		return "categories/list";
	}

	@GetMapping("/categories/new")
	public String showCategoryForm(Model model) {
		model.addAttribute("category", new CategoryDTO(null, ""));
		return "categories/form";
	}

	@GetMapping("/categories/{id}")
	public String viewCategory(@PathVariable Long id, Model model) {
		CategoryDTO category = categoryService.getCategoryById(id);
		model.addAttribute("category", category);
		model.addAttribute("tools", toolService.getAllTools().stream()
				.filter(t -> t.category_id() != null && t.category_id().equals(id)).toList());
		return "categories/view";
	}

	@GetMapping("/categories/edit/{id}")
	public String editCategory(@PathVariable Long id, Model model) {
		model.addAttribute("category", categoryService.getCategoryById(id));
		return "categories/form";
	}
}