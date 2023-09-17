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
        if (!entregadorRepository.existsById(entregadorId)) throw new EntregadorNaoCadastradoException();
        if (!estabelecimenRepository.existsById(estabelecimentoId)) throw  new EstabelecimentoNaoExisteException();
        entregadorValidaCodigoAcessoService.validacodigoAcessoService(entregadorId, codigoAcesso);
        return associacaoRepository.save(
                Associacao.builder()
                        .entregadorId(entregadorId)
                        .estabelecimentoId(estabelecimentoId)
                        .status(false)
                        .build()
        );
    }
}
