package com.ufcg.psoft.commerce.dto.sabor;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborPostPutRequestDTO {

    @JsonProperty("nome")
    @NotBlank(message = "Obrigatorio informar o nome do sabor.")
    private String nome;

    @JsonProperty("tipo")
    @NotBlank(message = "Obrigatorio informar o tipo do sabor.")
    private String tipo;

    @JsonProperty("precoM")
    @NotNull(message = "O valor nao pode ser nulo")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal precoM;

    @JsonProperty("precoG")
    @NotNull(message = "O valor nao pode ser nulo")
    @Positive(message = "O valor deve ser maior que zero")
    private BigDecimal precoG;

    @JsonProperty("disponivel")
    private Boolean disponivel;


    public Boolean isDisponivel(){
        return getDisponivel();
    }
}