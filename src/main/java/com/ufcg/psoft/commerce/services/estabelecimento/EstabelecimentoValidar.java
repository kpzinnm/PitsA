package com.ufcg.psoft.commerce.services.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoValidar {

    public void validar(Long id, String codigoAcesso);
}
