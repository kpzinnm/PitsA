package com.ufcg.psoft.commerce.dto.pizza;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PizzaPostPutDTO {

    @NotNull(message = "Sabor1 obrigatorio!")
    @JsonProperty("sabor1")
    private SaborPostPutRequestDTO sabor1;

    @JsonProperty("sabor2")
    private SaborPostPutRequestDTO sabor2;

    @JsonProperty("tamanho")
    @NotBlank(message = "Tamanho obrigatorio!")
    @Pattern(regexp = "^(grande|media)$", message = "O tamanho deve ser 'grande' ou 'media'")
    private String tamanho;
}
