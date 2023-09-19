package com.ufcg.psoft.commerce.services.associacao;

import com.ufcg.psoft.commerce.exception.EntregadorCodigoDeAcessoIncorretoException;
import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Associacao;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.AssociacaoRepository;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.services.entregador.EntregadorValidaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssociacaoV1CreateService implements AssociacaoCreateService{

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    EstabelecimentoRepository estabelecimenRepository;

    @Autowired
    EntregadorValidaCodigoAcessoService entregadorValidaCodigoAcessoService;

    @Override
    public Associacao create(Long entregadorId, String codigoAcesso, Long estabelecimentoId) {
        Optional<Entregador> entregador = entregadorRepository.findById(entregadorId);
        if (entregador.isEmpty()) {
            throw new EntregadorNaoCadastradoException("O entregador consultado nao existe!");
        }
        if (!estabelecimenRepository.existsById(estabelecimentoId)) {
            throw  new EstabelecimentoNaoExisteException("O estabelecimento consultado nao existe!");
        }
        if(!entregador.get().getCodigoAcesso().equals(codigoAcesso)){
            throw new EntregadorCodigoDeAcessoIncorretoException("Codigo de acesso invalido!");
        }
        return associacaoRepository.save(
                Associacao.builder()
                        .entregadorId(entregadorId)
                        .estabelecimentoId(estabelecimentoId)
                        .status(false)
                        .build()
        );
    }
}
