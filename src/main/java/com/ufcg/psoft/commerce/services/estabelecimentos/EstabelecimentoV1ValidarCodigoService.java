package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1ValidarCodigoService implements EstabelecimentoValidarCodigoService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public Boolean estabelecimentoValidaCodigoAcesso(Long id, String codigoAcesso) {
        if(codigoAcesso.equals(repository.findById(id).get().getCodigoAcesso())){
            return Boolean.TRUE;
        } else {
            throw new EstabelecimentoCodigoAcessoDiferenteException();
        }
    }
}
