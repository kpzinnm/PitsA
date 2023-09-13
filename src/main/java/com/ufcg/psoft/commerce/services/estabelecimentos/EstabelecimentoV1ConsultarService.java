package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EstabelecimentoV1ConsultarService implements EstabelecimentoConsultarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public Optional<Estabelecimento> consultarEstabelecimento(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<Estabelecimento> consultarTodosEstabelecimentos() {
        return repository.findAll();
    }
}
