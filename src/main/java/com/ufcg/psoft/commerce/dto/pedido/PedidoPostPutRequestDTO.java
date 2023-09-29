package com.ufcg.psoft.commerce.dto.pedido;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
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

    @JsonProperty("pizzas")
    private List<PizzaPostPutDTO> pizzas;

}