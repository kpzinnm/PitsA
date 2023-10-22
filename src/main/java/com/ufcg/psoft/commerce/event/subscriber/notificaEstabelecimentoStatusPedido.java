package com.ufcg.psoft.commerce.event.subscriber;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ufcg.psoft.commerce.event.NotificaEstabelecimentoStatusPedidoEvent;

@Component
public class notificaEstabelecimentoStatusPedido {
    
    @EventListener
    public void notificaEstabelecimento(NotificaEstabelecimentoStatusPedidoEvent event){
        String idEstabelecimento = event.getPedido().getEstabelecimentoId().toString();
        String idPedido = event.getPedido().getId().toString();
        System.out.println("Estabelecimento id: " + idEstabelecimento + " o pedido do id: " + idPedido + " foi entregue!");
    }
}
