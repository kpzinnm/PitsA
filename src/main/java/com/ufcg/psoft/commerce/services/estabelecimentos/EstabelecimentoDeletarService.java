package com.ufcg.psoft.commerce.services.estabelecimentos;

@FunctionalInterface
public interface EstabelecimentoDeletarService {

    public void deletarEstabelecimento(Long id, String codigoAcesso);
}
