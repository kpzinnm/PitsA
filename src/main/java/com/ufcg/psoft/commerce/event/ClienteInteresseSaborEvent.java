package com.ufcg.psoft.commerce.event;

import com.ufcg.psoft.commerce.event.eventModels.ClienteInteressadoEvent;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClienteInteresseSaborEvent extends ApplicationEvent {

    private ClienteInteressadoEvent clienteInteresse;

    public ClienteInteresseSaborEvent(Object source, ClienteInteressadoEvent clienteInteresse){
        super(source);
        this.clienteInteresse = clienteInteresse;
    }
}
