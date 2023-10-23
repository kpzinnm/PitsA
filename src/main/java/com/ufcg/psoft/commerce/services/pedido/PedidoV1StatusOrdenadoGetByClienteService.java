package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PedidoV1StatusOrdenadoGetByClienteService implements PedidoStatusOrdenadoGetByClienteService {

    @Autowired
    PedidoGetAllByClienteService pedidoGetAllByClienteService;


    @Override
    public List<Pedido> listarPedidosCliente(Long clienteId) {
        List<Pedido> pedidos = pedidoGetAllByClienteService.listarPedidosCliente(clienteId);

        pedidos.sort(  (pedido1, pedido2) -> {
            int comparacaoStatus = comparaStatus(pedido1.getStatus(), pedido2.getStatus());

            if (comparacaoStatus != 0) {
                return comparacaoStatus;
            }
            else
                return pedido2.getId().compareTo(pedido1.getId());
        });

        return pedidos;
    }

    private int comparaStatus(String status1, String status2) {

        // forneci um valor de prioridade para cada status
        Map<String, Integer> statusPrioridade = new HashMap<>();
        statusPrioridade.put("Recebido", 1);
        statusPrioridade.put("Em_preparo", 2);
        statusPrioridade.put("Pronto", 3);
        statusPrioridade.put("Em_Rota", 4);
        statusPrioridade.put("Entregue", 5);

        Integer prioridadeX = statusPrioridade.get(status1);
        Integer prioridadeY = statusPrioridade.get(status2);

        // Compara as prioridades
        return Integer.compare(prioridadeX, prioridadeY);
    }

}
