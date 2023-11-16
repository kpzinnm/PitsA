package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCadastrarService {

    public Estabelecimento cadastrarEstabelecimento(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO);
}
