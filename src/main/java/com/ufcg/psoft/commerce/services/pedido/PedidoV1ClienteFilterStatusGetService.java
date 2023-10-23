package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PedidoV1ClienteFilterStatusGetService implements PedidoClienteFilterStatusGetService {

    @Autowired
    PedidoGetAllByClienteService pedidoGetAllByClienteService;

    @Override
    public List<Pedido> listarPedidosClientePorStatus(Long clienteId, String status) {
        List<Pedido> pedidos = pedidoGetAllByClienteService.listarPedidosCliente(clienteId);

        // filtra os pedidos de acordo com o status fornecido
        if (status != null && !status.isEmpty()) {
            pedidos = pedidos.stream()
                    .filter(pedido -> pedido.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        // ordena de acordo com o tempo que o pedido fio criado (usando a id do pedido)
        pedidos.sort(Comparator.comparing(Pedido::getId).reversed());

        return pedidos;
    }
}
