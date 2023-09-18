package com.ufcg.psoft.commerce.services.sabor;
@FunctionalInterface
public interface SaborDeleteService {
    public void delete(Long id, Long estabelecimentoId, String estabelecimentoCodigoAcesso);
}
