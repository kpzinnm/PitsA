package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.model.PizzaInterface;

import java.math.BigDecimal;
import java.util.List;

@FunctionalInterface
public interface GerarPizzaFunction {
    PizzaInterface gerarPizza(PizzaPostPutDTO pizzaDTO);
}