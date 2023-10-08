package com.ufcg.psoft.commerce.services.estabelecimento;

public interface EstabelecimentoRecebeInteresseService {

    public void recebeInteresse(Long saborId, Long clienteId, Long estabelecimentoId, String estabelecimentoCodigo);
}
