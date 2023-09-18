package com.ufcg.psoft.commerce.dto.sabores;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Estabelecimento;

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
    @NotNull(message = "O valor nao pode ser nulo")
    private Boolean disponivel;

    @JsonProperty("estabelecimentos")
    private Set<Estabelecimento> estabelecimentos;

    public Boolean isDisponivel(){
        return getDisponivel();
    }
}