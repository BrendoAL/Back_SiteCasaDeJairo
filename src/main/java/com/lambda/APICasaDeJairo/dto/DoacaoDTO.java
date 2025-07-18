package com.lambda.APICasaDeJairo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class DoacaoDTO {

    @NotBlank(message = "Nome do doador é obrigatório")
    private String nomeDoador;
    @NotNull(message = "Valor da doação é obrigatório")
    @Positive(message = "Valor da doação deve ser positivo")
    private BigDecimal valor;
    private String metodoPagamento;
    private String mensagem;

    public DoacaoDTO(
            @NotBlank(message = "Nome do doador é obrigatório")
            String nomeDoador,
            @NotNull(message = "Valor da doação é obrigatório")
            @Positive(message = "Valor da doação deve ser positivo")
            BigDecimal valor,
            String metodoPagamento,
            String mensagem) {
        this.nomeDoador = nomeDoador;
        this.valor = valor;
        this.metodoPagamento = metodoPagamento;
        this.mensagem = mensagem;
    }

    public String getNomeDoador() {
        return nomeDoador;
    }

    public void setNomeDoador(String nomeDoador) {
        this.nomeDoador = nomeDoador;
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

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }
}
