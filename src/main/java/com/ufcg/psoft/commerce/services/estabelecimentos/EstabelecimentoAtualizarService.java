package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoAtualizarService {

    public Estabelecimento atualizarEstabelecimento(Long id, EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO, String codigoAcesso);
}
