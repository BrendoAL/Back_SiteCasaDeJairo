package com.lambda.APICasaDeJairo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//configuracao do acesso do Frontend ao Backend
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // permite acesso da sua aplicação Angular
                registry.addMapping("/api/**")
                        .allowedOriginPatterns("http://localhost:4200", "https://casadejairo.online", "https://www.casadejairo.online")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowCredentials(true);
            }
        };
    }
}
