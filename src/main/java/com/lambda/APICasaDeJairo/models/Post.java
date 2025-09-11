package com.lambda.APICasaDeJairo.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String conteudo;
    private LocalDateTime dataDoPost = LocalDateTime.now();
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "post_imagem_id", referencedColumnName = "id")
    private PostImagem postImagem;

    //getters e setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public LocalDateTime getDataDoPost() {
        return dataDoPost;
    }

    public void setDataDoPost(LocalDateTime dataDoPost) {
        this.dataDoPost = dataDoPost;
    }

    public PostImagem getPostImagem() {
        return postImagem;
    }

    public void setPostImagem(PostImagem postImagem) {
        this.postImagem = postImagem;
    }
}

