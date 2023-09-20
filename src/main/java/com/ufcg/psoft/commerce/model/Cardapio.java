package com.ufcg.psoft.commerce.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.dto.sabores.SaborCardapioDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Cardapio")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Cardapio {

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_cardapio")
    private Long id;

    @JsonProperty("sabores")
    @OneToOne
    private Estabelecimento estabelecimento;

    @OneToMany
    private Set<Sabor> sabores;

}
