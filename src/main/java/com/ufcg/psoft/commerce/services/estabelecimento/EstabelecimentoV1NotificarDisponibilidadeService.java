package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.event.ClienteInteresseSaborEvent;
import com.ufcg.psoft.commerce.model.ClienteInteresse;
import com.ufcg.psoft.commerce.repository.ClientesInteressadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstabelecimentoV1NotificarDisponibilidadeService implements EstabelecimentoNotificarDisponibilidadeService {

    @Autowired
    private ClientesInteressadosRepository clientesInteressadosRepository;

    @Autowired
    private ApplicationEventPublisher publisher;

    private List<ClienteInteresse> clienteInteresses;

    public void inicializarClienteInteresses() {
        clienteInteresses = clientesInteressadosRepository.findAll();
    }
    @Override
    public void notificarDisponibilidadeSabor(Long saborId) {

        inicializarClienteInteresses();

        clienteInteresses.forEach(clienteInteresse -> {
                   /* if (clienteInteresse.getSabor().getId().equals(saborId)) {
                        publisher.publishEvent(new ClienteInteresseSaborEvent(this, clienteInteresse));
                    }*/
                });
    }
}
