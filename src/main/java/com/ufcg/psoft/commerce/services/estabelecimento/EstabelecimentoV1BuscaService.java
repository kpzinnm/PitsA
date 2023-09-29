package com.ufcg.psoft.commerce.services.estabelecimento;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;

@Service
public class EstabelecimentoV1BuscaService implements EstabelecimentoBuscarService{

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public List<Estabelecimento> listarTodos() {
        return estabelecimentoRepository.findAll();
    }
    
}
