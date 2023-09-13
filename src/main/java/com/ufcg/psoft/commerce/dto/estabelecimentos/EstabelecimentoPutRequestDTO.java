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
public class EstabelecimentoPutRequestDTO {

    @JsonProperty("codigoAcesso")
    private String codigoAcesso;
}
