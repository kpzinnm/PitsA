package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.services.sabor.SaborCadastraService;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1CadastrarService implements EstabelecimentoCadastrarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Autowired
    private SaborCadastraService saborCadastraService;

    @Override
    public Estabelecimento cadastrarEstabelecimento(Estabelecimento estabelecimento) {
        return repository.save(estabelecimento);
    }
}
