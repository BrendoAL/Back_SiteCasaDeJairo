package com.lambda.APICasaDeJairo.exceptions;

// Violação de regra de negócio
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
