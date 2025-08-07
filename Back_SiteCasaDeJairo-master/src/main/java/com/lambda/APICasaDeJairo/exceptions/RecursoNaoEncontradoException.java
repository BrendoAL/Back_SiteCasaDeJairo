package com.lambda.APICasaDeJairo.exceptions;

// Recurso não encontrado
public class RecursoNaoEncontradoException extends RuntimeException {
    public RecursoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}

