package com.ufcg.psoft.commerce.event.subscriber;

import com.ufcg.psoft.commerce.event.NotificaClienteStatusPedidoEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificaClienteStatusPedido {
    @EventListener
    public void notificaClienteStatusPedido(NotificaClienteStatusPedidoEvent notificaClienteStatusPedidoEvent){
        String nomeEntregador = notificaClienteStatusPedidoEvent.getEntregador().getNome();
        String idCliente = notificaClienteStatusPedidoEvent.getCliente().getId().toString();
        System.out.println("CLiente notifica: " + idCliente + "o seu pedido está em rota, entregue por: " + nomeEntregador);
    }
}
