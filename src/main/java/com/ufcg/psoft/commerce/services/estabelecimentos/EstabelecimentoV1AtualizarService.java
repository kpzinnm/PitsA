package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1AtualizarService implements EstabelecimentoAtualizarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public Estabelecimento atualizarEstabelecimento(Long id, EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO) {
        Estabelecimento estabelecimento = this.repository.findById(id).get();
        if(estabelecimentoPutRequestDTO.getCodigoAcesso().length() == 6){
            estabelecimento = Estabelecimento.builder()
                    .id(estabelecimento.getId())
                    .codigoAcesso(estabelecimentoPutRequestDTO.getCodigoAcesso())
                    .build();
            return estabelecimento;
        } else {
            throw new EstabelecimentoCodigoAcessoInvalidoException();
        }
    }
}
