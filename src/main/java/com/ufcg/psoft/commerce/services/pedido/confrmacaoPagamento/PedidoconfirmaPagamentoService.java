package com.ufcg.psoft.commerce.services.pedido.confrmacaoPagamento;


import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoconfirmaPagamentoService {
    public Pedido confirmaPagamento(Long clienteId, String clienteCodAcesso, Long pedidoId, String metodoPagamento);
}