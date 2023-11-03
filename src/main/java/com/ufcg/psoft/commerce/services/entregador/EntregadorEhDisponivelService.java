package com.ufcg.psoft.commerce.services.entregador;

import org.springframework.stereotype.Service;

@FunctionalInterface
public interface EntregadorEhDisponivelService {

    public Boolean entregadorEhDisponivel(Long idEntregador);

}
