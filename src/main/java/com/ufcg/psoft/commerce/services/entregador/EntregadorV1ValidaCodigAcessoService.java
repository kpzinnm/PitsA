package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.exception.EntregadorCodigoDeAcessoIncorretoException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1ValidaCodigAcessoService implements EntregadorValidaCodigoAcessoService{
    @Autowired
    EntregadorRepository entregadorRepository;

    @Override
    public void validacodigoAcessoService(Long idEntregador, String codigoAcesso) {
        Entregador entregador = entregadorRepository.findById(idEntregador).get();

        if(!entregador.getCodigoAcesso().equals(codigoAcesso)){
            throw new EntregadorCodigoDeAcessoIncorretoException();
        }
    }
}
