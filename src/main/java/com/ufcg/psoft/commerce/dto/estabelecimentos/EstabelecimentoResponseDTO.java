package com.ufcg.psoft.commerce.dto.estabelecimentos;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("codigo")
    private String codigoAcesso;
}
