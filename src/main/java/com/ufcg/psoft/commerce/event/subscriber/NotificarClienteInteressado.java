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
        Long idSabor = event.getClienteInteresse().getIdSabor();
        System.out.println(nomecliente + " o sabor com id " + idSabor + " está novamente disponivel.");
    }
}
