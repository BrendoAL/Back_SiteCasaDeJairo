package com.lambda.APICasaDeJairo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

//transporta informações sem expor dados sensiveis
public class VoluntarioDTO {
    private String nome;
    private String email;
    private String telefone;
    private String disponibilidade;
    private String mensagem;
    private Boolean aceitaEmails;

    // Construtores
    public VoluntarioDTO() {}

    public VoluntarioDTO(String nome, String email, String telefone, String disponibilidade, String mensagem, Boolean aceitaEmails) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.disponibilidade = disponibilidade;
        this.mensagem = mensagem;
        this.aceitaEmails = aceitaEmails;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public void setDisponibilidade(String disponibilidade) {
        this.disponibilidade = disponibilidade;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Boolean getAceitaEmails() {
        return aceitaEmails;
    }

    public void setAceitaEmails(Boolean aceitaEmails) {
        this.aceitaEmails = aceitaEmails;
    }
}