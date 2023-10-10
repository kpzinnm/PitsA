package com.ufcg.psoft.commerce.dto.cliente;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClienteInteresseDTO {

    @JsonProperty("cliente")
    private String nomeCliente;

    @JsonProperty("sabor")
    private Long idSabor;

}
