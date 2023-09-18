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
@Entity(name = "associacao")
public class Associacao {

    @JsonProperty("id")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_associacao")
    private Long id;

    @JsonProperty("entregadorId")
    @Column(name = "fk_id_associacao", nullable = false)
    private Long entregadorId;

    @JsonProperty("estabelecimentoId")
    @Column(name = "fk_id_estabelecimento", nullable = false)
    private Long estabelecimentoId;

    @JsonProperty("status")
    @Column(name = "bool_status", nullable = false)
    private boolean status;
}
