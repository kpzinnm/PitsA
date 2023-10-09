package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

import java.util.List;

@FunctionalInterface
public interface PedidoGetAllByEstabelecimentoService {

    public List<Pedido> listarPedidosEstabelecimento(Long estabelecimentoId);
}
