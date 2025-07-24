package com.lambda.APICasaDeJairo.exceptions;

// Dados inválidos
public class DadosInvalidosException extends RuntimeException {
    public DadosInvalidosException(String mensagem) {
        super(mensagem);
    }
}
