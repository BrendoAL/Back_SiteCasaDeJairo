package com.lambda.APICasaDeJairo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiCasaDeJairoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCasaDeJairoApplication.class, args);
	}
}

@Bean
CommandLineRunner init(UserRepository userRepository, PasswordEncoder encoder) {
	return args -> {
		if (userRepository.findByUsername("admin").isEmpty()) {
			User admin = new User();
			admin.setUsername("admin");
			admin.setPassword(encoder.encode("admin123"));
			admin.setRoles(Set.of("ROLE_ADMIN"));
			userRepository.save(admin);
		}
	};
}

