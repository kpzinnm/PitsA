package com.ufcg.psoft.commerce.services.pedido.confrmacaoPagamento;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.model.Pedido;

@Service
public class PedidoV1confirmaPagamentoService implements PedidoconfirmaPagamentoService{

    private HashMap<String, MetodosPagamentoService> metodosPagamento;
    @Autowired
    PedidoPagarPorCartaoCredito pedidoPagarPorCartaoCredito;
    @Autowired
    PedidoPagarPorCartaoDebito pedidoPagarPorCartaoDebito;
    @Autowired
    PedidoPagarPorPix pedidoPagarPorPix;

    @Override
    public Pedido confirmaPagamento(Long clienteId, String clienteCodAcesso, Long pedidoId, String metodoPagamento) {
        metodosPagamento = new HashMap<>();
        metodosPagamento.put("Cartão de crédito", pedidoPagarPorCartaoCredito);
        metodosPagamento.put("Cartão de débito", pedidoPagarPorCartaoDebito);
        metodosPagamento.put("PIX", pedidoPagarPorPix);
        
        return metodosPagamento.get(metodoPagamento).confirmaPagamentoService(clienteId, clienteCodAcesso, pedidoId, metodoPagamento);
    }
    
}