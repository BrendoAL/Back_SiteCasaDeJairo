package com.lambda.APICasaDeJairo.dto;

//transporta informações sem expor dados sensiveis
public class PostDTO {
    private String titulo;
    private String conteudo;
    private Long postImagemId;

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

    public Long getPostImagemId() {
        return postImagemId;
    }

    public void setPostImagemId(Long postImagemId) {
        this.postImagemId = postImagemId;
    }
}

