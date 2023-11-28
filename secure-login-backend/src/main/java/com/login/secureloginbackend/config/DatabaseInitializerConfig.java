package com.login.secureloginbackend.config;

import com.login.secureloginbackend.model.Role;
import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import com.login.secureloginbackend.util.PasswordEncodeService;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
@AllArgsConstructor
public class DatabaseInitializerConfig {

    private UserRepository userRepository; // Suponiendo que tienes un repositorio para usuarios
    private PasswordEncodeService passwordEncoder; // Suponiendo que tienes un servicio para encriptar contraseñas
    @PostConstruct
    public void initialize() {
        if (!adminUserExists()) {
            createAdminUser();
        }
    }

    private boolean adminUserExists() {
        return userRepository.findUserModelByRole(Role.ADMIN).isPresent();

    }

    private void createAdminUser() {
        UserModel adminUser = UserModel.builder()
                .userId(UUID.randomUUID())
                .firstName("Sapetin")
                .lastName("Delgado")
                .email("adminemailtest@outlook.com")
                .role(Role.ADMIN)
                .password(passwordEncoder.encode("UID2ijkfsdK"))
                .lastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                .build();

        userRepository.save(adminUser);
    }
}

