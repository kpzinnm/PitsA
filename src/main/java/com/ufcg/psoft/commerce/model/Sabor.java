package com.ufcg.psoft.commerce.model;

import java.math.BigDecimal;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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

    @JsonProperty("estabelecimentos")
    @ManyToMany()
    @JoinTable(
        name = "estabelecimentos_sabores",
        joinColumns = @JoinColumn(name = "fk_id_sabor"),
        inverseJoinColumns = @JoinColumn(name = "fk_id_estabelecimento")
    )
    private Set<Estabelecimento> estabelecimentos;

    public Boolean isDisponivel(){
        return getDisponivel();
    }
}