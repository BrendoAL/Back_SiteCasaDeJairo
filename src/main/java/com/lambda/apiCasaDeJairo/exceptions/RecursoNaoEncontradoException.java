package com.lambda.apiCasaDeJairo.exceptions;

// Recurso não encontrado
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

