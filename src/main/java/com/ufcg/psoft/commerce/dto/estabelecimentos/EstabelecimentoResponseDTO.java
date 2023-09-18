package com.ufcg.psoft.commerce.dto.estabelecimentos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Sabor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstabelecimentoResponseDTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("codigoAcesso")
    private String codigoAcesso;

    @JsonProperty("sabores")
    private Set<Sabor> sabores;
}
