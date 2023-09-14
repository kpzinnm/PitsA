package com.ufcg.psoft.commerce.dto.sabor;

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

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("precoM")
    private double precoM;

    @JsonProperty("precoG")
    private double precoG;
}