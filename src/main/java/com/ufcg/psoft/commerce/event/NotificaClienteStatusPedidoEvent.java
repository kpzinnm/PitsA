package com.ufcg.psoft.commerce.event;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.model.Entregador;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class NotificaClienteStatusPedidoEvent extends ApplicationEvent {
    ClienteGetDTO cliente;
    Entregador entregador;

    public NotificaClienteStatusPedidoEvent(Object source, ClienteGetDTO cliente, Entregador entregador){
        super(source);
        this.cliente = cliente;
        this.entregador = entregador;
    }
}
