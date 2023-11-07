package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.model.Associacao;

import java.util.Set;

public interface AssociacaoPegarService {
    public Associacao pegarAssociacao(Long associacaoId);

    public Set<Long> pegarEstabelecimentoIdsDoEntregador(Long entregadorId);

    public Set<Long> pegarEntregadorIdsDoEstabelecimento(Long estabelecimentoId);
}
