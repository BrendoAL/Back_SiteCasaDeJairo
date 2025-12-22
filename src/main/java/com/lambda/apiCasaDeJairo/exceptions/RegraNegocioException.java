package com.lambda.apiCasaDeJairo.exceptions;

// Violação de regra de negócio
public class RegraNegocioException extends RuntimeException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
