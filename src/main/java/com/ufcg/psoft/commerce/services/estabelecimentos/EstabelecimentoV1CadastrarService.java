package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1CadastrarService implements EstabelecimentoCadastrarService {

    @Autowired
    private EstabelecimentoRepository repository;


    @Override
    public Estabelecimento cadastrarEstabelecimento(Estabelecimento estabelecimento) {
        return repository.save(estabelecimento);
    }
}
