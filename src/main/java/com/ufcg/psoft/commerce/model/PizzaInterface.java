package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.math.BigDecimal;

public interface PizzaInterface {
    public BigDecimal calculoDePreco();
}
