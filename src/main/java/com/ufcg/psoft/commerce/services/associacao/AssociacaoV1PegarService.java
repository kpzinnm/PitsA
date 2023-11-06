package com.ufcg.psoft.commerce.services.associacao;


import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AssociacaoV1PegarService implements AssociacaoPegarService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Override
    public Associacao pegarAssociacao(Long associacaoId) {
        return associacaoRepository.findById(associacaoId).get();
    }

    @Override
    public Set<Long> pegarEstabelecimentoIdsDoEntregador(Long entregadorId) {
        List<Associacao> associacoes = associacaoRepository.findAll();
        Set<Associacao> associacoesEntregador = associacoes
                .stream()
                .filter(
                        associacao -> associacao.getEntregadorId().equals(entregadorId) && associacao.isStatus()
                ).collect(Collectors.toSet());
        return associacoesEntregador.stream().map(
                Associacao::getEstabelecimentoId
        ).collect(Collectors.toSet());
    }

    @Override
    public Set<Long> pegarEntregadorIdsDoEstabelecimento(Long estabelecimentoId) {
        List<Associacao> associacoes = associacaoRepository.findAll();
        Set<Associacao> associacoesEntregador = associacoes
                .stream()
                .filter(
                        associacao -> associacao.getEstabelecimentoId().equals(estabelecimentoId) && associacao.isStatus()
                ).collect(Collectors.toSet());
        return associacoesEntregador.stream().map(
                Associacao::getEntregadorId
        ).collect(Collectors.toSet());
    }
}
