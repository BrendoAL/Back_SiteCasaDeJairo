package com.lambda.APICasaDeJairo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


//transporta informações sem expor dados sensiveis
public class EmpresaParceiraDTO {

    private String nome;
    private String email;
    private String telefone;
    private String mensagem;

    public EmpresaParceiraDTO() {
    }

    public EmpresaParceiraDTO(String nome, String email, String telefone, String mensagem) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
        this.mensagem = mensagem;
    }

    // Validações e Getters
    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, message = "Nome deve ter pelo menos 3 caracteres")
    public String getNome() {
        return nome;
    }

    @Email(message = "Email inválido")
    @NotBlank(message = "Email é obrigatório")
    public String getEmail() {
        return email;
    }

    public String getTelefone() {
        return telefone;
    }

    @NotBlank(message = "Mensagem é obrigatória")
    @Size(min = 10, message = "Mensagem deve ter pelo menos 10 caracteres")
    public String getMensagem() {
        return mensagem;
    }

    // Setters
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
