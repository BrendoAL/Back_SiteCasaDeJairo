package com.lambda.APICasaDeJairo.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Transparencia")
public class Transparencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String descricao;
    private LocalDate data;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_imagem_id")
    private PostImagem postImagem;

    // Getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }

    public PostImagem getPostImagem() { return postImagem; }
    public void setPostImagem(PostImagem postImagem) { this.postImagem = postImagem; }
}