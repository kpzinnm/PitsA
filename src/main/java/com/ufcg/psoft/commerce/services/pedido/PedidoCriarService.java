package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface PedidoCriarService {

    public Pedido criarPedido(
            Long clienteId,
            String clienteCodigoAcesso,
            Long estabelecimentoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    );
}
