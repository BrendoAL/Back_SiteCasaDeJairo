package com.lambda.APICasaDeJairo.dto;

import java.time.LocalDate;

public class TransparenciaDTO {
    private Long id;
    private String titulo;
    private String descricao;
    private Long postImagemId;
    private LocalDate dataPublicacao;

    public TransparenciaDTO() {}

    public TransparenciaDTO(String titulo, String descricao, Long postImagemId, LocalDate dataPublicacao) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.postImagemId = postImagemId;
        this.dataPublicacao = dataPublicacao;
    }

    // getters e setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public Long getPostImagemId() { return postImagemId; }
    public void setPostImagemId(Long postImagemId) { this.postImagemId = postImagemId; }

    public LocalDate getDataPublicacao() { return dataPublicacao; }
    public void setDataPublicacao(LocalDate dataPublicacao) { this.dataPublicacao = dataPublicacao; }
}
