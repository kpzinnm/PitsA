package com.ufcg.psoft.commerce.dto.sabores;

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
public class SaborCardapioDTO {

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("precoM")
    private BigDecimal precoM;

    @JsonProperty("precoG")
    private BigDecimal precoG;

}