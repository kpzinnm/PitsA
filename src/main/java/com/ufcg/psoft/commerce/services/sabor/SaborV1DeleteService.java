package com.ufcg.psoft.commerce.services.sabor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.SaborNotExistException;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimentos.EstabelecimentoValidar;

@Service
public class SaborV1DeleteService implements SaborDeleteService{

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;
    
    @Autowired
    SaborRepository saborRepository;

    @Override
    public void delete(Long id, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoId, estabelecimentoCodigoAcesso);

        if(saborRepository.existsById(id)){
            saborRepository.deleteById(id);
        } else {
            throw new SaborNotExistException();
        }
    }
    
}
