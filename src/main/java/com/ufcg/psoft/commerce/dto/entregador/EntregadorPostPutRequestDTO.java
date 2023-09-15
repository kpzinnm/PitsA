package com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntregadorPostPutRequestDTO {
    @NotBlank(message = "Nome do entregador obrigatorio")
    @JsonProperty("nome")
    private String nome;

    @NotBlank(message = "Placa do veiculo obrigatoria")
    @JsonProperty("placaVeiculo")
    private String placaVeiculo;

    @NotBlank(message = "Tipo do veiculo obrigatorio")
    @JsonProperty("tipoVeiculo")
    private String tipoVeiculo;

    @NotBlank(message = "Cor do veiculo obrigatoria")
    @JsonProperty("corVeiculo")
    private String corVeiculo;

    @NotBlank(message = "Codigo de acesso obrigatorio")
    @Pattern(regexp = "\\d{6}", message = "O código de acesso deve conter exatamente 6 dígitos numericos")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}
