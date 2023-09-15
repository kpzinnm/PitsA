package com.ufcg.psoft.commerce.dto.sabor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("precoM")
    private BigDecimal precoM;

    @JsonProperty("precoG")
    private BigDecimal precoG;

    @JsonProperty("disponivel")
    private Boolean disponivel;

    public Boolean isDisponivel(){
        return getDisponivel();
    }
}