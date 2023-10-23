package com.ufcg.psoft.commerce.event.subscriber;

import com.ufcg.psoft.commerce.event.ClienteInteresseSaborEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificarClienteInteressado {

    @EventListener
    public void notificarCliente(ClienteInteresseSaborEvent event){
        String nomecliente = event.getClienteInteresse().getCliente().getNome();
        String nomeSabor = event.getClienteInteresse().getSabor().getNome();
        System.out.println(nomecliente + ", o sabor " + nomeSabor + " está novamente disponivel.");
    }
}
