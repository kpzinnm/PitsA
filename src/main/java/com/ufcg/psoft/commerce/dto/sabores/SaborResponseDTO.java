package com.ufcg.psoft.commerce.dto.sabores;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.ufcg.psoft.commerce.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SaborResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("nome")
    private String nome;

    @JsonProperty("tipo")
    private String tipo;

    @JsonProperty("precoM")
    private BigDecimal precoM;

    @JsonProperty("precoG")
    private BigDecimal precoG;

    @JsonProperty("disponivel")
    private Boolean disponivel;

    @JsonProperty("clienteInteressados")
    private List<Cliente> clientesInteressados;


    public Boolean isDisponivel(){
        return getDisponivel();
    }
}