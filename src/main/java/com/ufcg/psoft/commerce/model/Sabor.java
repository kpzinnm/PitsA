package com.ufcg.psoft.commerce.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "sabores")
public class Sabor {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false, name = "desc_nome", unique = true)
    @JsonProperty("nome")
    private String nome;

    @Column(nullable = false, name = "desc_tipo")
    @JsonProperty("tipo")
    private String tipo;

    @Column(nullable = false, name = "desc_precoM")
    @JsonProperty("precoM")
    private double precoM;

    @Column(nullable = false, name = "desc_precoG")
    @JsonProperty("precoG")
    private double precoG;

    @Column(nullable = false)
    @JsonProperty("disponivel")
    private boolean disponivel;
}