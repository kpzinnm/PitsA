package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Pedido;
import org.springframework.stereotype.Service;

@Service
public interface PedidoNotificaStatusEventManager {
    public void notificaEstabelecimentoStatusEntrega(Pedido pedido);
    public void notificaClienteStatusEntrega(ClienteGetDTO cliente, Entregador entregador);
}
