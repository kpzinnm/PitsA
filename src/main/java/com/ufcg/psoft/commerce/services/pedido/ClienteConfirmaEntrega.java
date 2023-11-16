package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

@FunctionalInterface
public interface ClienteConfirmaEntrega {
    public PedidoResponseDTO confirmaEntrega(Long pedidoId, Long clienteId, String clienteCodigoAcesso, PedidoPutRequestDTO pedidoPutRequestDTO);
}
