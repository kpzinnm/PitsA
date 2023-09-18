package com.ufcg.psoft.commerce.services.sabor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.SaborNotExistException;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimentos.EstabelecimentoValidar;

@Service
public class SaborV1GetService implements SaborGetService {

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    EstabelecimentoRepository  estabelecimentoRepository;

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;
    
    @Override
    public List<Sabor> getAll(Long estabelecimentoID, String codigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoID, codigoAcesso);

        return saborRepository.findAll();
    }

    @Override
    public Sabor getSaborById(Long id, Long estabelecimentoID, String codigoAcesso) {
        this.estabelecimentoValidar.validar(estabelecimentoID, codigoAcesso);
        
        if(saborRepository.existsById(id)){
            return saborRepository.findById(id).get();
        } else {
            throw new SaborNotExistException();
        }
    }
    
}
