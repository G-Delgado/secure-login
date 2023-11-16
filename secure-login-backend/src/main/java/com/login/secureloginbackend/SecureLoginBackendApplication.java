package com.login.secureloginbackend;

import com.login.secureloginbackend.model.UserModel;
import com.login.secureloginbackend.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SecureLoginBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureLoginBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(UserRepository userRepository){

		UserModel admin = UserModel.builder()
				.firstName("Admin")
				.lastName("Admin")
				.email("admin@hotmail.com")
				.password("admin")
				.phoneNumber("3154620529")
				.isAdmin(true)
				.build();

		UserModel user1 = UserModel.builder()
				.firstName("John")
				.lastName("Doe")
				.email("jd@hotmail.com")
				.password("123456")
				.phoneNumber("3154620528")
				.isAdmin(false)
				.build();

		UserModel user2 = UserModel.builder()
				.firstName("Karla")
				.lastName("Doe")
				.email("kd@hotmail.com")
				.password("admin123")
				.phoneNumber("3185372546")
				.isAdmin(false)
				.build();

		UserModel user3 = UserModel.builder()
				.firstName("Carlos")
				.lastName("Doe")
				.email("cd@hotmail.com")
				.password("admin123456")
				.phoneNumber("3185372545")
				.isAdmin(false)
				.build();

		return args -> {
			userRepository.save(admin);
			userRepository.save(user1);
			userRepository.save(user2);
			userRepository.save(user3);
		};

	}


}
