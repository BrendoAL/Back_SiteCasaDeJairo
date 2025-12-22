package com.lambda.apiCasaDeJairo.jwt;

//Cria um token de autenticação
class JwtResponse {
    private String token;
    public JwtResponse(String token) { this.token = token; }
    public String getToken() { return token; }
}
