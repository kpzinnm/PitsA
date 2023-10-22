package com.ufcg.psoft.commerce.event;

import org.springframework.context.ApplicationEvent;

import com.ufcg.psoft.commerce.model.Pedido;

import lombok.Getter;

@Getter
public class NotificaEstabelecimentoStatusPedidoEvent extends ApplicationEvent {

    private Pedido pedido;

    public NotificaEstabelecimentoStatusPedidoEvent(Object source, Pedido pedido){
        super(source);
        this.pedido = pedido;
    }
    
}
