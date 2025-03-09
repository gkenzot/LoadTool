	package com.LoadTool.user;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // Retorna todos os usuários não deletados
    public List<UserDTO> getAllUsers() {
        return userRepository.findAllNotDeleted()
                .stream()
                .map(UserDTO::fromEntity)
                .toList();
    }

    // Busca um usuário por ID, lançando exceção se não encontrado
    public UserDTO getUserById(Long id) {
        return userRepository.findByIdAndNotDeleted(id)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(id));
    }

    // Busca um usuário por e-mail, lançando exceção se não encontrado
    public UserDTO getUserByEmail(String email) {
        return userRepository.findByEmailAndNotDeleted(email)
                .map(UserDTO::fromEntity)
                .orElseThrow(() -> new UserNotFoundException(email));
    }

    // Salva um novo usuário
    @Transactional
    public UserDTO saveUser(User user) {
        if (userRepository.findByEmailAndNotDeleted(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("E-mail já está em uso.");
        }
        user.setCreatedAt(Instant.now().toEpochMilli());
        User savedUser = userRepository.save(user);
        return UserDTO.fromEntity(savedUser);
    }
    
    // Atualiza os dados
    @Transactional
    public UserDTO updateUser(Long id, User updatedUser) {
        // Busca o usuário existente pelo ID
        User existingUser = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        // Atualiza os campos permitidos
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setPhone(updatedUser.getPhone());
        existingUser.setAddress(updatedUser.getAddress());

        // Salva o usuário atualizado
        User savedUser = userRepository.save(existingUser);
        return UserDTO.fromEntity(savedUser);
    }

    // Soft delete: marca o usuário como deletado
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findByIdAndNotDeleted(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        user.setDeletedAt(Instant.now().toEpochMilli()); // Define a data de exclusão
        userRepository.save(user);
    }
}