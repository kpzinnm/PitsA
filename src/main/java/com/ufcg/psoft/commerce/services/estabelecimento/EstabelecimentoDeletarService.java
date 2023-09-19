package com.ufcg.psoft.commerce.services.estabelecimento;

@FunctionalInterface
public interface EstabelecimentoDeletarService {

    public void deletarEstabelecimento(Long id, String codigoAcesso);
}
