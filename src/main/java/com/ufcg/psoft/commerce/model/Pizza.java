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
@Entity(name = "pizzas")
public class Pizza {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pizza")
    private Long id;

    @Column(nullable = false, name = "desc_sabor1")
    @JsonProperty("sabor1")
    private Sabor sabor1;

    @Column(nullable = false, name = "desc_sabor2")
    @JsonProperty("sabor2")
    private Sabor sabor2;

    @Column(nullable = false, name = "desc_tamanho")
    @JsonProperty("tamanho")
    private String tamanho;

    @ManyToOne
    private Pedido pedido;
}