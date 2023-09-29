package com.ufcg.psoft.commerce.services.associacao;


import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssociacaoV1PegarService implements AssociacaoPegarService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Override
    public Associacao pegarAssociacao(Long associacaoId) {
        return associacaoRepository.findById(associacaoId).get();
    }
}
