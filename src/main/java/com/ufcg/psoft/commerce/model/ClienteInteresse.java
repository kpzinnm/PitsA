package com.ufcg.psoft.commerce.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@NoArgsConstructor
public class ClienteInteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "pk_id_clienteInteresse")
    private Long id;

    @JsonProperty("cliente")
    private String nomeCliente;

    private Long idSabor;

    public ClienteInteresse(String cliente, Long idSabor) {
        this.nomeCliente = cliente;
        this.idSabor = idSabor;
    }

}
