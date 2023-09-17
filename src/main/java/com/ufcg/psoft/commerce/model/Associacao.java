package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "associacao")
public class Associacao {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_associacao")
    private Long id;

    @Column(name = "fk_id_associacao", nullable = false)
    private Long entregadorId;

    @Column(name = "fk_id_estabelecimento", nullable = false)
    private Long estabelecimentoId;

    @Column(name = "bool_status", nullable = false)
    private boolean status;
}
