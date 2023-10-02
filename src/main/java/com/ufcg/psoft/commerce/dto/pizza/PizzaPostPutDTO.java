package com.ufcg.psoft.commerce.dto.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaPostPutDTO {

    @NotNull(message = "Necessario ao menos um sabor!")
    @JsonProperty("sabor1")
    private SaborPostPutRequestDTO sabor1;

    @JsonProperty("sabor2")
    private SaborPostPutRequestDTO sabor2;

    @JsonProperty("tamanho")
    @NotBlank(message = "tamanho obrigatorio!")
    private String tamanho;
}
