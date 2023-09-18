package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.services.entregador.EntregadorValidaCodigoAcessoService;
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
