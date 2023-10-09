package com.ufcg.psoft.commerce.services.pedido;


import com.ufcg.psoft.commerce.exception.PedidoNotExistException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class PedidoV1GetService implements PedidoGetService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public Pedido pegarPedido(
            Long pedidoId
    ) {
        if (pedidoRepository.existsById(pedidoId)) {
            return pedidoRepository.findById(pedidoId).get();
        } else {
            throw new PedidoNotExistException();
        }
    }
}
