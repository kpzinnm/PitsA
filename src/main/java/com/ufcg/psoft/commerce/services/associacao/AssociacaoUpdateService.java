package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoUpdateService {
    public Associacao update(Long entregadorId, String codigoAcesso, Long estabelecimentoId);
}
