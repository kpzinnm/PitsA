package com.ufcg.psoft.commerce.services.estabelecimentos;

@FunctionalInterface
public interface EstabelecimentoValidarCodigoService {

    public Boolean estabelecimentoValidaCodigoAcesso(Long id, String codigoAcesso);
}
