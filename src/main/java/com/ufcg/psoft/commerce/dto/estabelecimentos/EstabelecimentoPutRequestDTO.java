package com.ufcg.psoft.commerce.dto.estabelecimentos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Sabor;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoPutRequestDTO {

    @JsonProperty("codigoAcesso")
    @Size(min = 6, max = 6, message = "Codigo de acesso deve ter exatamente 6 digitos numericos")
    private String codigoAcesso;

    @JsonProperty("sabores")
    private Set<Sabor> sabores;
}
