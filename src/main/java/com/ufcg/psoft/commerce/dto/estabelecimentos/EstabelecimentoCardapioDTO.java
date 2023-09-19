package com.ufcg.psoft.commerce.dto.estabelecimentos;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ufcg.psoft.commerce.model.Sabor;

public class EstabelecimentoCardapioDTO {
    @JsonProperty("sabores")
    private Set<Sabor> sabores;
}
