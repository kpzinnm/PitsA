package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;

@FunctionalInterface
public interface PedidoCriarService {

    public Pedido criarPedido(
            Long clienteId,
            String clienteCodigoAcesso,
            Long estabelecimentoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    );
}
