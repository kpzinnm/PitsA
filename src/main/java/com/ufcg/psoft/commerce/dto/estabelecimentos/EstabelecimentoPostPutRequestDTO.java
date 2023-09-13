package com.ufcg.psoft.commerce.dto.estabelecimentos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstabelecimentoPostPutRequestDTO {

    @JsonProperty("codigo")
    private String codigoAcesso;

}
