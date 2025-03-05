package com.LoadTool.auth;

import com.LoadTool.user.User;
import com.LoadTool.user.UserDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody LoginRequest loginRequest) {
        // Valida o login
        User user = authService.validateLogin(loginRequest.email(), loginRequest.password());

        // Converte o usuário para DTO e retorna
        return ResponseEntity.ok(UserDTO.fromEntity(user));
    }
}