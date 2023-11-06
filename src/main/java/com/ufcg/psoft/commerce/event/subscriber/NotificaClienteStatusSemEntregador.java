package com.ufcg.psoft.commerce.event.subscriber;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ufcg.psoft.commerce.event.NotificaClienteStatusEntregadorEvent;

@Component
public class NotificaClienteStatusSemEntregador {
    
    @EventListener
    public void notificaClienteStatusSemEntregador(NotificaClienteStatusEntregadorEvent event){
        String nomeCliente = event.getCliente().getNome();
        System.out.println(nomeCliente + ", infelizmente estamos sem entregador no momento!");
    }
}
