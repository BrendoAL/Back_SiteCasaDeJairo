package com.lambda.APICasaDeJairo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class VoluntarioDTO {

    private String nome;
    private String email;
    private String telefone;

    public VoluntarioDTO(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

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

    @NotBlank(message = "Telefone é obrigatório")
    public String getTelefone() {
        return telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
