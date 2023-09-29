package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1CadastrarService implements EstabelecimentoCadastrarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Autowired
    private SaborRepository saborRepository;

    @Override
    public Estabelecimento cadastrarEstabelecimento(EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        Set<Sabor> sabores = estabelecimentoPostPutRequestDTO.getSabores();

        Set<Sabor> saboresDefault = new HashSet<>();

        Estabelecimento estabelecimento = Estabelecimento.builder()
                .codigoAcesso(estabelecimentoPostPutRequestDTO.getCodigoAcesso())
                .sabores(saboresDefault)
                .build();

        if (sabores != null) {
            for (Sabor sabor : sabores) {
                saborRepository.save(sabor);
            }

            estabelecimento.setSabores(sabores);
        }

        return repository.save(estabelecimento);
    }
}