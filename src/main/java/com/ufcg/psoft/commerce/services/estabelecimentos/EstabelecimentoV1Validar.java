package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1Validar implements EstabelecimentoValidar {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public void validar(Long id, String codigoAcesso) {
        if(!(this.repository.existsById(id)))
            throw new EstabelecimentoNaoExisteException();
        if(!(codigoAcesso.equals(repository.findById(id).get().getCodigoAcesso()))){
            throw new EstabelecimentoCodigoAcessoDiferenteException();
        }
 }
}
