package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoClienteDeleteService {

    public void clienteDeletaPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso);
}
