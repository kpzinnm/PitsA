package com.ufcg.psoft.commerce.model;

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
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_entregador")
    private Long id;

    @Column(nullable = false, name = "desc_nome")
    private String nome;

    @Column(nullable = false, name = "desc_placaVeiculo")
    private String placaVeiculo;

    @Column(nullable = false, name = "desc_tipoVeiculo")
    private String tipoVeiculo;

    @Column(nullable = false, name = "desc_corVeiculo")
    private String corVeiculo;

    @Column(nullable = false, name = "desc_codigoAcesso")
    private String codigoAcesso;
}
