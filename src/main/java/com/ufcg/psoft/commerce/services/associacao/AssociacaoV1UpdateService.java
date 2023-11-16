package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorCodigoDeAcessoIncorretoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.services.entregador.EntregadorValidaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociacaoV1UpdateService implements AssociacaoUpdateService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    EstabelecimentoRepository estabelecimenRepository;

    @Autowired
    EntregadorValidaCodigoAcessoService entregadorValidaCodigoAcessoService;

    @Override
    public Associacao update(Long entregadorId, String codigoAcesso, Long estabelecimentoId) {
        Optional<Estabelecimento> estabelecimento = estabelecimenRepository.findById(estabelecimentoId);
        if (!entregadorRepository.existsById(entregadorId)) {
            throw new EntregadorNaoCadastradoException("O entregador consultado nao existe!");
        }
        if (estabelecimento.isEmpty()) {
            throw  new EstabelecimentoNaoExisteException("O estabelecimento consultado nao existe!");
        }
        if(!estabelecimento.get().getCodigoAcesso().equals(codigoAcesso)){
            throw new EntregadorCodigoDeAcessoIncorretoException("Codigo de acesso invalido!");
        }
        Associacao associacao = associacaoRepository
                .findByEntregadorIdAndEstabelecimentoId(entregadorId, estabelecimentoId).get();
        associacao.setStatus(true);
        return associacaoRepository.save(associacao);
    }
}
