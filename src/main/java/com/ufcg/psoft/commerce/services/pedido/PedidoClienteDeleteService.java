package com.ufcg.psoft.commerce.services.pedido;



public interface PedidoClienteDeleteService {

    public void clienteDeletaPedido(Long pedidoId, Long clienteId, String clienteCodigoAcesso);
    public void clienteDeleteTodosPedidosFeitos(Long clienteId);
    public void cancelarPedido(Long pedidoId, String clienteCodigoAcesso);
}
