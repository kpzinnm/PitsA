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

    @Id
    @JsonProperty("id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_pedido")
    private Long id;

    @Column(nullable = false, name = "desc_preco")
    @JsonProperty("preco")
    private BigDecimal preco;

    @Column(nullable = false, name = "desc_endereco_entrega")
    @JsonProperty("enderecoEntrega")
    private BigDecimal enderecoEntrega;

    @Column(nullable = false, name = "desc_cliente_id")
    @JsonProperty("clienteId")
    private BigDecimal clienteId;

    @Column(nullable = false, name = "desc_estabelecimento_id")
    @JsonProperty("estabelecimentoId")
    private BigDecimal estabelecimentoId;

    @Column(nullable = false, name = "desc_entregador_id")
    @JsonProperty("entregadorId")
    private BigDecimal entregadorId;

    @JsonProperty("pizzas")
    @OneToMany
    private List<Pizza> pizzas;

}