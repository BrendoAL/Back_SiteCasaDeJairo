package com.lambda.APICasaDeJairo.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

//Cria entidade no banco de dados
@Entity
@Table(name = "voluntarios")
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String nome;

    @Email
    @NotBlank
    @Column(nullable = false, unique = true)
    private String email;

    private String telefone;

    private String disponibilidade;

    @Column(length = 1000)
    private String mensagem;

    @Column(name = "aceita_emails", nullable = false)
    private Boolean aceitaEmails = false;

    // Construtores
    public Voluntario() {}

    public Voluntario(String nome, String email, String telefone, String disponibilidade, String mensagem, Boolean aceitaEmails) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.disponibilidade = disponibilidade;
        this.mensagem = mensagem;
        this.aceitaEmails = aceitaEmails;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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