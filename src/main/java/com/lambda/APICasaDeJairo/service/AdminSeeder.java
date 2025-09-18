package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.models.User;
import com.lambda.APICasaDeJairo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
public class AdminSeeder {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Value("${ADMIN_USERNAME}")
    private String adminUsername;

    @Value("${ADMIN_PASSWORD}")
    private String adminPassword;

    public AdminSeeder(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seedAdmin() {
        if (userRepo.findByUsername(adminUsername).isEmpty()) {
            User admin = new User();
            admin.setUsername(adminUsername);
            admin.setPassword(passwordEncoder.encode(adminPassword));
            admin.setRoles(Set.of("ROLE_ADMIN")); // role como Set
            userRepo.save(admin);
            System.out.println("✅ Usuário admin criado: " + adminUsername);
        } else {
            System.out.println("ℹ️ Usuário admin já existe: " + adminUsername);
        }
    }
}
