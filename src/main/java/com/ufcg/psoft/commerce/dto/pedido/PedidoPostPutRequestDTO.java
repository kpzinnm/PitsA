package com.ufcg.psoft.commerce.dto.pedido;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoPostPutRequestDTO {

    @JsonProperty("enderecoEntrega")
    private String enderecoEntrega;

    @NotEmpty(message = "Necessario ao menos uma pizza")
    @JsonProperty("pizzas")
    private List<PizzaPostPutDTO> pizzas;

}