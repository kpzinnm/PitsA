package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.model.Pedido;



public interface PedidoAlterarService {

    public Pedido alterarPedido(
            String clienteCodigoAcesso,
            Long pedidoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    );

    public PedidoResponseDTO associarEntregador(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso,
            PedidoPutRequestDTO pedidoPutRequestDTO);

    public PedidoResponseDTO setPedidoPronto(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);

}
