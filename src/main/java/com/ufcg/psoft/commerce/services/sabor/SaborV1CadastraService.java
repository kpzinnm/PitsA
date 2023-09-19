package com.ufcg.psoft.commerce.services.sabor;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

@Service
public class SaborV1CadastraService implements SaborCadastraService {

    @Autowired
    private SaborRepository saborRepository;
    
    @Autowired
    private EstabelecimentoValidar estabelecimentoValidar;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Override
    public Sabor cadastrarSabor(SaborPostPutRequestDTO saborPostPutRequestDTO, Long id, String codigoAcesso) {
        this.estabelecimentoValidar.validar(id, codigoAcesso);

        Estabelecimento estabelecimentoCurrent = estabelecimentoRepository.findById(id).get();

        Sabor sabor = saborRepository.save(
            Sabor.builder()
            .disponivel(saborPostPutRequestDTO.getDisponivel())
            .estabelecimentos(saborPostPutRequestDTO.getEstabelecimentos())
            .nome(saborPostPutRequestDTO.getNome())
            .tipo(saborPostPutRequestDTO.getTipo())
            .precoG(saborPostPutRequestDTO.getPrecoG())
            .precoM(saborPostPutRequestDTO.getPrecoM())
            .build()
        );

        Set<Sabor> sabores = new HashSet<>();

        sabores.add(sabor);


        adicionarSaborPizza(estabelecimentoCurrent, sabores);
        
        return sabor;
    }
    
    private void adicionarSaborPizza(Estabelecimento estabelecimento, Set<Sabor> sabor){
        estabelecimento.setSabores(sabor);
    }
}
