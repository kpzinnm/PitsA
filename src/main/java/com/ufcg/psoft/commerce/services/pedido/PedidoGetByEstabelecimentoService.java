package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoGetByEstabelecimentoService {

    public Pedido pegarPedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);
}
