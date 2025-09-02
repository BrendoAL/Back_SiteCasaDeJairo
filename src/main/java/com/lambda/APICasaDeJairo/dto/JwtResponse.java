package com.lambda.APICasaDeJairo.dto;

//Cria um token de autenticação
public class JwtResponse {
    private String token;
    public JwtResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
