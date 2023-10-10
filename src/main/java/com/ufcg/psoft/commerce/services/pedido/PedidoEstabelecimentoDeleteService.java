package com.ufcg.psoft.commerce.services.pedido;


public interface PedidoEstabelecimentoDeleteService {
    public void deletePedido(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso, String codigoAcesso);
    public void deleteTodosPedidos(Long estabelecimentoId, String codigoAcesso);
}
