package com.ufcg.psoft.commerce.services.pedido;

import java.util.List;

import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;

public interface PedidoClienteBuscarService {
    public PedidoResponseDTO buscarPedidoById(Long clienteId, Long estabelecimentoId, Long pedidoId, String clienteCodigoAcesso);
    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso);
    public List<PedidoResponseDTO> buscarPedidoPorStatus(Long clienteId, Long estabelecimentoId, String status,
            String clienteCodigoAcesso);
    public List<PedidoResponseDTO> buscarPedidosPorEntrega(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso);
}
