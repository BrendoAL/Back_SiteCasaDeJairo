package com.lambda.apiCasaDeJairo.jwt;

import com.lambda.apiCasaDeJairo.auth.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//verifica se há um token JWT no cabeçalho da requisição http, se o token for válido, vai autenticar o usuario.
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        System.out.println("Auth Header: " + authHeader);

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            System.out.println(" Token extraído: " + token.substring(0, Math.min(50, token.length())) + "...");

            try {
                String username = jwtService.extractUsername(token);
                System.out.println(" Username do token: " + username);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    UserDetails userDetails = userService.loadUserByUsername(username);
                    System.out.println(" Roles do usuário: " + userDetails.getAuthorities());

                    if (jwtService.isTokenValid(token)) {
                        UsernamePasswordAuthenticationToken authToken =
                                new UsernamePasswordAuthenticationToken(
                                        userDetails,
                                        null,
                                        userDetails.getAuthorities()
                                );
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                        System.out.println("Usuário autenticado com sucesso");
                    } else {
                        System.out.println("Token inválido");
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro no filtro JWT: " + e.getMessage());
            }
        }

        filterChain.doFilter(request, response);
    }
}