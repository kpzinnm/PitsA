package com.ufcg.psoft.commerce.services.estabelecimentos;

import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeletarService implements EstabelecimentoDeletarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public void deletarEstabelecimento(Long id) {
        this.repository.deleteById(id);
    }
}
