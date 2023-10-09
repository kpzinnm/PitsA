package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EstabelecimentoV1PegarService implements EstabelecimentoPegarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public Estabelecimento pegarEstabelecimento(Long id) {
        Estabelecimento estabelecimento = this.repository.findById(id).get();
        if(estabelecimento == null) {
            throw new EstabelecimentoNaoExisteException();
        } else {
            return estabelecimento;
        }
    }
}
