package com.lambda.APICasaDeJairo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//configuracao do spring security
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // desabilita proteção CSRF
            .authorizeHttpRequests()
                .anyRequest().permitAll() // permite tudo sem autenticação
            .and()
            .headers().frameOptions().disable(); // libera o console H2 

        return http.build();
    }
}

