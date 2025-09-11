package com.lambda.APICasaDeJairo.security;

import com.lambda.APICasaDeJairo.models.User;
import com.lambda.APICasaDeJairo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@Order(1)
public class AdminSeeder implements CommandLineRunner {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    public AdminSeeder(UserRepository userRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        System.out.println("🚀 AdminSeeder construtor chamado!");
    }

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("🔧 AdminSeeder INICIANDO...");
        System.out.println("=".repeat(50));

        try {
            // Verificar quantos usuários existem
            long userCount = userRepo.count();
            System.out.println("📊 Total de usuários no banco: " + userCount);

            // Verificar se admin existe
            var adminOptional = userRepo.findByUsername("admin");

            if (adminOptional.isEmpty()) {
                System.out.println("➕ Criando usuário admin...");

                User admin = new User();
                admin.setUsername("admin");

                // Criptografar senha
                String rawPassword = "B8ryTh3L1ght";
                String encodedPassword = passwordEncoder.encode(rawPassword);
                admin.setPassword(encodedPassword);

                // Definir roles
                admin.setRoles(Set.of("ROLE_ADMIN"));

                // Salvar
                User savedAdmin = userRepo.save(admin);

                System.out.println("✅ Admin criado com ID: " + savedAdmin.getId());
                System.out.println("📋 Credenciais:");
                System.out.println("   Username: admin");
                System.out.println("   Password: B8ryTh3L1ght");
                System.out.println("   Password encoded: " + encodedPassword.substring(0, 20) + "...");

            } else {
                User existingAdmin = adminOptional.get();
                System.out.println("⚠️ Admin já existe!");
                System.out.println("   ID: " + existingAdmin.getId());
                System.out.println("   Username: " + existingAdmin.getUsername());
                System.out.println("   Roles: " + existingAdmin.getRoles());
                System.out.println("   Password starts with: " + existingAdmin.getPassword().substring(0, 10) + "...");

                // Verificar se a senha está bem encodada
                if (!existingAdmin.getPassword().startsWith("$2")) {
                    System.out.println("🔧 Senha não está em formato BCrypt, corrigindo...");
                    existingAdmin.setPassword(passwordEncoder.encode("B8ryTh3L1ght"));
                    userRepo.save(existingAdmin);
                    System.out.println("✅ Senha corrigida!");
                }
            }

            // Verificar total de usuários após operação
            long finalUserCount = userRepo.count();
            System.out.println("📊 Total de usuários após operação: " + finalUserCount);

        } catch (Exception e) {
            System.err.println("❌ ERRO no AdminSeeder: " + e.getMessage());
            e.printStackTrace();
        }

        System.out.println("=".repeat(50));
        System.out.println("🏁 AdminSeeder FINALIZADO");
        System.out.println("=".repeat(50) + "\n");
    }
}