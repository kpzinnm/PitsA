package com.ufcg.psoft.commerce.event;

import org.springframework.context.ApplicationEvent;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;

import lombok.Getter;

@Getter
public class NotificaClienteStatusEntregadorEvent extends ApplicationEvent{
    
    private ClienteGetDTO cliente;

    public NotificaClienteStatusEntregadorEvent(Object source, ClienteGetDTO cliente){
        super(source);
        this.cliente = cliente;
    }
}
