package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Set;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pizzas_grandes")
public class PizzaGrande implements PizzaInterface {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pizza_grande")
    private Long id;

    @JsonProperty("sabores")
    @ManyToMany
    private Set<Sabor> sabores;

    @ManyToOne
    private Pedido pedido;

    @Override
    public BigDecimal calculoDePreco() {
        BigDecimal total = new BigDecimal(0);
        for (Sabor sabor: this.sabores) {
            total.add(sabor.getPrecoG());
        }
        return total;
    }
}