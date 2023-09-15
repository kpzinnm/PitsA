package com.ufcg.psoft.commerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_cliente")
    private Long id;

    @Column(nullable = false, name = "desc_nome")
    private String nome;

    @Column(nullable = false, name = "desc_endereco")
    private String endereco;

    @Column(nullable = false, length = 6, name = "desc_codigoAcesso")
    private String codigoAcesso;

}
