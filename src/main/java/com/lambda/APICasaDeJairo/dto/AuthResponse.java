package com.lambda.APICasaDeJairo.dto;

//Cria um token de autenticação
public class AuthResponse {
    private String token;
    public AuthResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
