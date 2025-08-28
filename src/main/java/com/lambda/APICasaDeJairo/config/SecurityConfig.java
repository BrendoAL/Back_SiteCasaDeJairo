package com.lambda.APICasaDeJairo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.lambda.APICasaDeJairo.security.JwtAuthFilter;
import com.lambda.APICasaDeJairo.service.UserService;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final UserService userService;
    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserService userService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userService = userService;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // rotas públicas
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/index.html/**",
                                "/swagger-ui/**",
                                "/h2-console/**",
                                "/api/auth/**")
                        .permitAll()
                        // GET público de eventos e transparência e voluntários
                        .requestMatchers(HttpMethod.GET, "/api/eventos/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/transparencia/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/voluntarios/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/empresa-parceira/**").permitAll()
                        // **POST público de voluntários e empresa parceira**
                        .requestMatchers(HttpMethod.POST, "/api/voluntarios/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/empresa-parceira/**").permitAll()

                        // POST, PUT, DELETE eventos → admin
                        .requestMatchers(HttpMethod.POST, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/eventos/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/eventos/**").hasRole("ADMIN")
                        // POST, PUT, DELETE transparência → admin
                        .requestMatchers(HttpMethod.POST, "/api/transparencia/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/transparencia/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/transparencia/**").hasRole("ADMIN")
                        // rotas /api/admin/** → admin
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        // todas as outras rotas precisam de autenticação
                        .anyRequest().authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}