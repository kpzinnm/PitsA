package com.ufcg.psoft.commerce.services.pedido.confrmacaoPagamento;

import com.ufcg.psoft.commerce.model.Pedido;

public interface MetodosPagamentoService {
    public Pedido confirmaPagamentoService(Long clienteId, String clienteCodAcesso, Long pedidoId, String metodoPagamento);
}
