package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1DeletarService implements EntregadorDelatarService{
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    EntregadorValidaCodigoAcessoService entregadorValidaCodigoAcessoService;
    @Override
    public void deletaEntregador(Long idEntregador, String codigoAcesso) {
        if(entregadorRepository.existsById(idEntregador)){
            entregadorValidaCodigoAcessoService.validacodigoAcessoService(idEntregador, codigoAcesso);
            entregadorRepository.deleteById(idEntregador);
        }else{
            throw new EntregadorNaoCadastradoException();
        }
    }
}
