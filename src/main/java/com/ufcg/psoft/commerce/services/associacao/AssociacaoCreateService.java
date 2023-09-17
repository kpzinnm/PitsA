package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.model.Associacao;

@FunctionalInterface
public interface AssociacaoCreateService {
    public Associacao create(Long entregadorId, String codigoAcesso, Long estabelecimentoId);
}
