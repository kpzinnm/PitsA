package com.ufcg.psoft.commerce.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;
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
    @Column(name = "pk_id_sabor")
    private Long id;

    @Column(nullable = false, name = "desc_nome")
    @JsonProperty("nome")
    private String nome;

    @Column(nullable = false, name = "desc_tipo")
    @JsonProperty("tipo")
    private String tipo;

    @Column(nullable = false, name = "desc_precoM")
    @JsonProperty("precoM")
    private BigDecimal precoM;

    @Column(nullable = false, name = "desc_precoG")
    @JsonProperty("precoG")
    private BigDecimal precoG;

    @Column(nullable = false, name = "bl_disponivel")
    @JsonProperty("disponivel")
    private Boolean disponivel;

    public Boolean isDisponivel(){ return getDisponivel();}
}