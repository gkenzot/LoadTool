package com.LoadTool.auth;

import com.LoadTool.util.MD5Util;
import com.LoadTool.user.User;
import com.LoadTool.user.UserNotFoundException;
import com.LoadTool.user.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Método para validar o login
    public User validateLogin(String email, String password) {
        User user = userRepository.findByEmailAndNotDeleted(email)
                .orElseThrow(() -> new UserNotFoundException(email));

        // Converte a senha fornecida para MD5 usando a classe utilitária
        String hashedPassword = MD5Util.hashMD5(password);

        // Compara a senha fornecida com a senha armazenada
        if (hashedPassword.equals(user.getPassword())) {
            return user; // Retorna o usuário se a senha estiver correta
        } else {
            throw new IllegalArgumentException("Senha incorreta");
        }
    }
}