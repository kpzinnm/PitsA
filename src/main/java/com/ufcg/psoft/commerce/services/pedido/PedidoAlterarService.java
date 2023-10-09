package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;



public interface PedidoAlterarService {

    public Pedido alterarPedido(
            String clienteCodigoAcesso,
            Long pedidoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    );

    public Pedido associarEntregador(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO);

}
