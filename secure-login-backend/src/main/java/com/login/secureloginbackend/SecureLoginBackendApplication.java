package com.login.secureloginbackend;

import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import lombok.SneakyThrows;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.login.secureloginbackend.util.PasswordEncodeService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@SpringBootApplication
public class SecureLoginBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureLoginBackendApplication.class, args);
	}

	@SneakyThrows
	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository){

		UserModel admin = UserModel.builder()
				.userId(UUID.randomUUID())
				.firstName("Admin")
				.lastName("Admin")
				.email("admin@hotmail.com")
				.password(PasswordEncodeService.encodePassword("admin"))
				.isAdmin(true)
				.lastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))

				.build();

		UserModel user1 = UserModel.builder()
				.userId(UUID.randomUUID())
				.firstName("John")
				.lastName("Doe")
				.email("jd@hotmail.com")
				.password(PasswordEncodeService.encodePassword("123456"))
				.isAdmin(false)
				.lastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
				.build();

		UserModel user2 = UserModel.builder()
				.userId(UUID.randomUUID())
				.firstName("Karla")
				.lastName("Doe")
				.email("kd@hotmail.com")
				.password(PasswordEncodeService.encodePassword("admin123"))
				.isAdmin(false)
				.lastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
				.build();

		UserModel user3 = UserModel.builder()
				.userId(UUID.randomUUID())
				.firstName("Carlos")
				.lastName("Doe")
				.email("cd@hotmail.com")
				.password(PasswordEncodeService.encodePassword("admin123456"))
				.isAdmin(false)
				.lastLogin(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
				.build();

		return args -> {
			userRepository.save(admin);
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
		};

	}


}
