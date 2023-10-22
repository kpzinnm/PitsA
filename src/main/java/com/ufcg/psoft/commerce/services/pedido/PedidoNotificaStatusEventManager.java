package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.model.Pedido;

public interface PedidoNotificaStatusEventManager {
    public void notificaEstabelecimentoStatusEntrega(Pedido pedido);
}
