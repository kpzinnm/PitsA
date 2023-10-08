package com.ufcg.psoft.commerce.event.subscriber;

import com.ufcg.psoft.commerce.event.ClienteInteresseSaborEvent;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificarClienteInteressado {

    @EventListener
    public void notificarCliente(ClienteInteresseSaborEvent event){
        String nomecliente = event.getClienteInteresse().getNomeCliente();
        String nomeSabor = event.getClienteInteresse().getNomeSabor();
        System.out.println(nomecliente + " o sabor da " + nomeSabor + " está novamente disponivel.");
    }
}
