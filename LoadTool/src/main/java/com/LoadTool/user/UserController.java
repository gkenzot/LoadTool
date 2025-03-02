package com.LoadTool.user;

import java.util.List;
import java.util.Optional;

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

@RestController
@RequestMapping("/api/users") // Define o caminho base para os endpoints de usuários
public class UserController {

    private final UserService userService;

    // Injeção de dependência do UserService via construtor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Endpoint para buscar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users); // Retorna a lista de usuários com status 200 OK
    }

    // Endpoint para buscar um usuário pelo ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        Optional<UserDTO> userDTO = userService.getUserById(id);
        return userDTO.map(ResponseEntity::ok) // Retorna o usuário se existir
                .orElse(ResponseEntity.notFound().build()); // Retorna 404 Not Found se não existir
    }

    // Endpoint para criar um novo usuário
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        UserDTO savedUserDTO = userService.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUserDTO); // Retorna o usuário criado com status 201 Created
    }

    // Endpoint para atualizar um usuário existente
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id); // Garante que o ID do usuário seja o mesmo da URL
        UserDTO updatedUserDTO = userService.saveUser(user);
        return ResponseEntity.ok(updatedUserDTO); // Retorna o usuário atualizado com status 200 OK
    }

    // Endpoint para excluir um usuário pelo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build(); // Retorna status 204 No Content
    }
}