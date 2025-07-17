package com.lambda.APICasaDeJairo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class DoacaoDTO {

    @NotBlank(message = "Nome do doador é obrigatório")
    private String nomeDoador;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email inválido")
    private String email;

    @NotNull(message = "Valor da doação é obrigatório")
    @Positive(message = "Valor da doação deve ser positivo")
    private BigDecimal valor;

    private String mensagem;

    public DoacaoDTO(@NotBlank(message = "Nome do doador é obrigatório") String nomeDoador,
            @NotBlank(message = "Email é obrigatório") @Email(message = "Email inválido") String email,
            @NotNull(message = "Valor da doação é obrigatório") @Positive(message = "Valor da doação deve ser positivo") BigDecimal valor,
            String mensagem) {
        this.nomeDoador = nomeDoador;
        this.email = email;
        this.valor = valor;
        this.mensagem = mensagem;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
