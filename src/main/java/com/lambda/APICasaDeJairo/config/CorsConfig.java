package com.lambda.APICasaDeJairo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//configuracao do acesso do front ao back
@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        //.allowedOrigins("http://localhost:4200", "https://www.casadejairo.com")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
