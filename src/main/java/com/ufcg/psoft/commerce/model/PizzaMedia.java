package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pizzas_medias")
public class PizzaMedia implements PizzaInterface {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pizza_media")
    private Long id;

    @JsonProperty("sabor")
    @ManyToOne
    private Sabor sabor;

    @ManyToOne
    private Pedido pedido;

    @Override
    public BigDecimal calculoDePreco() {
        return this.sabor.getPrecoM();
    }
}