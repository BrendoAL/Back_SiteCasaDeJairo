package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.models.User;
import com.lambda.APICasaDeJairo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

//main da aplicação
@SpringBootApplication
public class ApiCasaDeJairoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiCasaDeJairoApplication.class, args);
	}
}

