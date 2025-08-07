package com.lambda.APICasaDeJairo.config;

import com.lambda.APICasaDeJairo.models.User;
import com.lambda.APICasaDeJairo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Set;

@Configuration
@Profile("dev") // só executa quando o perfil ativo for 'dev'
public class DatabaseSeeder {

    @Bean
    public CommandLineRunner seedDatabase(UserRepository userRepository, PasswordEncoder encoder) {
        return args -> {
            User admin = userRepository.findByUsername("admin").orElse(null);

            if (admin == null) {
                admin = new User();
                admin.setUsername("admin");
                admin.setRoles(Set.of("ROLE_ADMIN"));
                System.out.println("✅ Criando usuário admin...");
            } else {
                System.out.println("ℹ️ Atualizando senha do usuário admin...");
            }

            admin.setPassword(encoder.encode("123456"));
            userRepository.save(admin);
        };
    }
}