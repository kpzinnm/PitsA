package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoV1GetAllByClienteService implements PedidoGetAllByClienteService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public List<Pedido> listarPedidosCliente(
            Long clienteId
    ) {
        List<Pedido> pedidoList = pedidoRepository.findAll();
        return pedidoList.stream().filter(pedido -> pedido.getClienteId().equals(clienteId)).toList();
    }
}
