package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1ValidarId implements EstabelecimentoValidarId {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public Boolean validarId(Long id) {
        if(!(this.repository.existsById(id)))
            throw new EstabelecimentoNaoExisteException();

        return Boolean.TRUE;
    }
}
