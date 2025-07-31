package com.lambda.APICasaDeJairo.dto;

//transporta informações sem expor dados sensiveis
public class PostDTO {
    private String titulo;
    private String conteudo;

    // Getters e setters

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
}

