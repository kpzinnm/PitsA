package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

@FunctionalInterface
public interface PedidoGetAllByClienteService {

    public List<Pedido> listarPedidosCliente(Long clienteId);
}
