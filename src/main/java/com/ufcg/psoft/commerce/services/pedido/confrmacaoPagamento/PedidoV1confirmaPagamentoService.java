package com.ufcg.psoft.commerce.services.pedido.confrmacaoPagamento;

import java.util.HashMap;
import java.util.Objects;

import com.ufcg.psoft.commerce.exception.PedidoNotReceivedException;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.pedido.PedidoGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.model.Pedido;

@Service
public class PedidoV1confirmaPagamentoService implements PedidoconfirmaPagamentoService {

    private HashMap<String, MetodosPagamentoService> metodosPagamento;
    @Autowired
    PedidoPagarPorCartaoCredito pedidoPagarPorCartaoCredito;
    @Autowired
    PedidoPagarPorCartaoDebito pedidoPagarPorCartaoDebito;
    @Autowired
    PedidoPagarPorPix pedidoPagarPorPix;

    @Autowired
    PedidoGetService pedidoGetService;

    @Autowired
    PedidoRepository pedidoRepository;
    @Override
    public Pedido confirmaPagamento(Long clienteId, String clienteCodAcesso, Long pedidoId, String metodoPagamento) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);

        if (Objects.equals(pedido.getStatus(), "Pedido recebido")) {
            metodosPagamento = new HashMap<>();
            metodosPagamento.put("Cartão de crédito", pedidoPagarPorCartaoCredito);
            metodosPagamento.put("Cartão de débito", pedidoPagarPorCartaoDebito);
            metodosPagamento.put("PIX", pedidoPagarPorPix);

            pedido = metodosPagamento.get(metodoPagamento).confirmaPagamentoService(
                    clienteId,
                    clienteCodAcesso,
                    pedidoId,
                    metodoPagamento
            );

            pedido.setStatusPagamento(true);
            pedido.setStatus("Pedido em preparo");
            pedidoRepository.flush();
            return pedido;
        } else {
            throw new PedidoNotReceivedException();
        }
    }
    
}