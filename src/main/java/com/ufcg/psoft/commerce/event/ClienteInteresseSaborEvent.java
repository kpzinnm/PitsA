package com.ufcg.psoft.commerce.event;

import com.ufcg.psoft.commerce.model.ClienteInteresse;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ClienteInteresseSaborEvent extends ApplicationEvent {

    private ClienteInteresse clienteInteresse;

    public ClienteInteresseSaborEvent(Object source, ClienteInteresse clienteInteresse){
        super(source);
        this.clienteInteresse = clienteInteresse;

    }
}
