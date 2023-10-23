package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.event.NotificaClienteStatusPedidoEvent;

import com.ufcg.psoft.commerce.model.Entregador;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.event.NotificaEstabelecimentoStatusPedidoEvent;
import com.ufcg.psoft.commerce.model.Pedido;

@Service
public class PedidoV1NotificaStatusEventManager implements PedidoNotificaStatusEventManager {
    
    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void notificaEstabelecimentoStatusEntrega(Pedido pedido) {
        publisher.publishEvent(new NotificaEstabelecimentoStatusPedidoEvent(this, pedido));    
    }

    @Override
    public void notificaClienteStatusEntrega(ClienteGetDTO cliente, Entregador entregador) {
        publisher.publishEvent(new NotificaClienteStatusPedidoEvent(this, cliente, entregador));
    }

}
