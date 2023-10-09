package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "entregador")
public class Entregador {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_entregador")
    private Long id;

    @JsonProperty("nome")
    @Column(nullable = false, name = "desc_nome")
    private String nome;

    @JsonProperty("placaVeiculo")
    @Column(nullable = false, name = "desc_placaVeiculo")
    private String placaVeiculo;

    @JsonProperty("tipoVeiculo")
    @Column(nullable = false, name = "desc_tipoVeiculo")
    private String tipoVeiculo;

    @JsonProperty("corVeiculo")
    @Column(nullable = false, name = "desc_corVeiculo")
    private String corVeiculo;

    @JsonProperty("codigoAcesso")
    @Column(nullable = false, name = "desc_codigoAcesso")
    private String codigoAcesso;

    @JsonProperty("statusAprovacao")
    @Column(nullable = false, name = "bl_status_provacao")
    @Builder.Default
    private boolean statusAprovacao = false;

    @JsonProperty("disponibilidade")
    @Column(nullable = false, name = "bl_disponibilidade")
    @Builder.Default
    private boolean disponibilidade = true;

}
