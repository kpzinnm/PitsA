package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.model.Estabelecimento;

@FunctionalInterface
public interface EstabelecimentoCadastrarService {

    public Estabelecimento cadastrarEstabelecimento(Estabelecimento estabelecimento);
}
