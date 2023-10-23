package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

public interface PedidoStatusOrdenadoGetByClienteService {

    public List<Pedido> listarPedidosCliente(Long clienteId);
}
