package com.ufcg.psoft.commerce.dto.entregador;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "Cor do veiculo Obrigatoria")
    @JsonProperty("corVeiculo")
    private String corVeiculo;

    @NotBlank(message = "Codigo de acesso Obrigatorio")
    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}
