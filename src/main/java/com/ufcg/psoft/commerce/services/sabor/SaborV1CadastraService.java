package com.ufcg.psoft.commerce.services.sabor;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimentos.EstabelecimentoValidar;

@Service
public class SaborV1CadastraService implements SaborCadastraService {

    @Autowired
    private SaborRepository saborRepository;
    
    @Autowired
    private EstabelecimentoValidar estabelecimentoValidar;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Sabor cadastrarSabor(Sabor sabor, Long id, String codigoAcesso) {
        this.estabelecimentoValidar.validar(id, codigoAcesso);

        Estabelecimento estabelecimentoCurrent = estabelecimentoRepository.findById(id).get();

        adicionarSaborPizza(estabelecimentoCurrent, sabor);
        
        return saborRepository.save(sabor);
    }
    
    private void adicionarSaborPizza(Estabelecimento estabelecimento, Sabor sabor){
        estabelecimento.setSabores((Set<Sabor>) sabor);
    }
}
