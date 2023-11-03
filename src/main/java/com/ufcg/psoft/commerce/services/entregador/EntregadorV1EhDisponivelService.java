package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
/**
 * Retorna a disponibilidade do Entregador, use este service para verificar se o entregador pode realizar uma entrega na US19*/
@Service
public class EntregadorV1EhDisponivelService implements EntregadorEhDisponivelService{

    @Autowired
    private EntregadorRepository entregadorRepository;

    @Override
    public Boolean entregadorEhDisponivel(Long idEntregador) {
        if(entregadorRepository.existsById(idEntregador)){

            return entregadorRepository.findById(idEntregador).get().isDisponibilidade();
        }
        throw new EntregadorNaoCadastradoException();
    }
}
