package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoPegarService {

    public Estabelecimento pegarEstabelecimento(Long id);
}
