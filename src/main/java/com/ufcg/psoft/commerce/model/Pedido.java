package com.ufcg.psoft.commerce.model;

import java.math.BigDecimal;
import java.util.List;

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
@Entity(name = "pedidos")
public class Pedido {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pedido")
    private Long id;

    @JsonProperty("preco")
    @Column(nullable = false, name = "desc_preco")
    private BigDecimal preco;

    @JsonProperty("enderecoEntrega")
    @Column(nullable = false, name = "desc_endereco_entrega")
    private String enderecoEntrega;

    @JsonProperty("clienteId")
    @Column(nullable = false, name = "desc_cliente_id")
    private Long clienteId;

    @JsonProperty("estabelecimentoId")
    @Column(nullable = false, name = "desc_estabelecimento_id")
    private Long estabelecimentoId;

    @JsonProperty("entregadorId")
    @Column(nullable = false, name = "desc_entregador_id")
    private Long entregadorId;

    @JsonProperty("pizzas")
    @OneToMany
    private List<Pizza> pizzas;

}