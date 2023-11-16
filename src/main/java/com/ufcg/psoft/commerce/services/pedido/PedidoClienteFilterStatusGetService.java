package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

public interface PedidoClienteFilterStatusGetService {

    public List<Pedido> listarPedidosClientePorStatus(Long clienteId, String status);
}
