package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.event.ClienteInteresseSaborEvent;
import com.ufcg.psoft.commerce.event.eventModels.ClienteInteressadoEvent;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Sabor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1NotificarDisponibilidadeService implements EstabelecimentoNotificarDisponibilidadeService {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void notificarDisponibilidadeSabor(Sabor sabor) {

        for(Cliente cliente : sabor.getClienteInteressados()){
            publisher.publishEvent(new ClienteInteresseSaborEvent(this, new ClienteInteressadoEvent(cliente, sabor)));
        }

    }
}
