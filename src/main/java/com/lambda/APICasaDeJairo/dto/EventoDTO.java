package com.lambda.APICasaDeJairo.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO (Data Transfer Object) utilizado para transferência de dados de um evento.
 * Contém validações para garantir que os campos obrigatórios sejam preenchidos corretamente.
 * Utilizado principalmente em operações de criação e atualização de eventos.
 */

public class EventoDTO {

    private Long id;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    private String descricao;

    @NotNull(message = "Data e hora são obrigatórios")
    @Future(message = "A data do evento deve ser no futuro")
    private LocalDate data;

    @NotBlank(message = "Local é obrigatório")
    private String local;

    private String imagemUrl;

    public EventoDTO(Long id, String titulo, String descricao, LocalDate data, String local, String imagemUrl) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
        this.local = local;
        this.imagemUrl = imagemUrl;
    }

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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}
