package com.lambda.apiCasaDeJairo.exceptions;

// Dados inválidos
public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
