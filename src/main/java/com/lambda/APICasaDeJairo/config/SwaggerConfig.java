package com.lambda.APICasaDeJairo.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path:}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Casa de Jairo - API")
                        .description("API para o website da ONG Casa de Jairo - Sistema de gerenciamento de eventos, transparência, voluntários e parcerias empresariais")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Casa de Jairo")
                                .email("casadejairo.adm@hotmail.com")
                                .url("https://casadejairo.online"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("https://back-sitecasadejairo.onrender.com")
                                .description("Servidor de Produção"),
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor Local de Desenvolvimento")))
                .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
                .components(new Components()
                        .addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .scheme("bearer")
                .description("Insira o token JWT aqui (sem o prefixo 'Bearer')");
    }
}
