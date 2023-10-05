package com.ufcg.psoft.commerce.services.pedido.confrmacaoPagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.services.pedido.PedidoGetByClienteService;

@Service
public class PedidoPagarPorPix implements MetodosPagamentoService{
    @Autowired
    PedidoGetByClienteService pedidoGetByClienteService;

    @Override
    public Pedido confirmaPagamentoService(Long clienteId, String clienteCodAcesso, Long pedidoId,
            String metodoPagamento) {

        Pedido pedido = pedidoGetByClienteService.pegarPedido(pedidoId, clienteId, clienteCodAcesso);
        pedido.aplicaDescontoPix();
        pedido.atualizaStatusPagamento();
        return pedido;
    }
    
}