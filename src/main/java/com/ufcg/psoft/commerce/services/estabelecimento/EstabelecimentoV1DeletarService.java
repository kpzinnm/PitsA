package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1DeletarService implements EstabelecimentoDeletarService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Autowired
    private EstabelecimentoValidar estabelecimentoValidar;

    @Override
    public void deletarEstabelecimento(Long id, String codigoAcesso) {
        this.estabelecimentoValidar.validar(id, codigoAcesso);
        this.repository.deleteById(id);
    }
}
