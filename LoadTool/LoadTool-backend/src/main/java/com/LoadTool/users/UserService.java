package com.LoadTool.users;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAllNotDeleted()
                .stream()
                .map(UserResponseDTO::fromEntity)
                .toList();
    }

    public UserResponseDTO getUserById(Long id) {
        return userRepository.findByIdAndNotDeleted(id)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    public UserResponseDTO getUserByEmail(String email) {
        return userRepository.findByEmailAndNotDeleted(email)
                .map(UserResponseDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        // Validação de e-mail
        if (userRepository.findByEmailAndNotDeleted(userRequestDTO.email()).isPresent()) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "E-mail já está em uso: " + userRequestDTO.email()
            );
        }

        // Validação adicional, se necessário
        validateUserData(userRequestDTO);

        User user = userRequestDTO.toEntity();
        user.setCreatedAt(Instant.now().toEpochMilli());

        User savedUser = userRepository.save(user);
        return UserResponseDTO.fromEntity(savedUser);
    }
    
    @Transactional
    public UserResponseDTO updateUser(Long id, UserRequestDTO userRequestDTO) {
        User existingUser = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Atualiza o usuário com os dados fornecidos
        userRequestDTO.updateEntity(existingUser);
        
        User savedUser = userRepository.save(existingUser);
        return UserResponseDTO.fromEntity(savedUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        
        // Marca como deletado
        user.setDeletedAt(Instant.now().toEpochMilli());
        userRepository.save(user);
    }

    // Método de validação adicional para os dados do usuário
    private void validateUserData(UserRequestDTO userRequestDTO) {
        if (userRequestDTO.email() == null || userRequestDTO.email().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "E-mail não pode ser vazio");
        }
        
        // Adicione outras validações de campos aqui, como nome, senha, etc.
    }
}
