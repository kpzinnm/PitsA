package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.model.PizzaInterface;


@FunctionalInterface
public interface GerarPizzaFunction {
    PizzaInterface gerarPizza(PizzaPostPutDTO pizzaDTO);
}