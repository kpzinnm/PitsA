package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoPegarService {
    public Associacao pegarAssociacao(Long associacaoId);
}
