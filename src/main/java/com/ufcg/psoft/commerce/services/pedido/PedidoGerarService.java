package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoGerarService {

    public Pedido gerarPedido(PedidoPostPutRequestDTO pedidoPostPutRequestDTO, ClienteGetDTO cliente,
                              Long estabelecimentoId);
}
