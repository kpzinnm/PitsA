package com.ufcg.psoft.commerce.services.estabelecimentos;

@FunctionalInterface
public interface EstabelecimentoValidar {

    public void validar(Long id, String codigoAcesso);
}
