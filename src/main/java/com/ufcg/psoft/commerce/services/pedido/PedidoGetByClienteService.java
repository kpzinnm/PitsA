package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoGetByClienteService {

    public Pedido pegarPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso);
}
