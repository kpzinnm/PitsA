package com.ufcg.psoft.commerce.services.sabor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

import java.util.ArrayList;

@Service
public class SaborV1CadastraService implements SaborCadastraService {

    @Autowired
    private SaborRepository saborRepository;

    @Autowired
    private EstabelecimentoValidar estabelecimentoValidar;

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private SaborVerificaTipoService saborVerificaTipoService;

    @Override
    public Sabor cadastrarSabor(SaborPostPutRequestDTO saborPostPutRequestDTO, Long id, String codigoAcesso) {

        saborVerificaTipoService.verificaTipo(saborPostPutRequestDTO.getTipo().toUpperCase());

        this.estabelecimentoValidar.validar(id, codigoAcesso);
        
        Estabelecimento estabelecimentoCurrent = estabelecimentoRepository.findById(id).get();


        Sabor sabor = saborRepository.save(
                Sabor.builder()
                        .disponivel(saborPostPutRequestDTO.getDisponivel())
                        .nome(saborPostPutRequestDTO.getNome())
                        .tipo(saborPostPutRequestDTO.getTipo())
                        .precoG(saborPostPutRequestDTO.getPrecoG())
                        .precoM(saborPostPutRequestDTO.getPrecoM())
                        .clienteInteressados(new ArrayList<>())
                        .build());


        // Set<Sabor> sabores = estabelecimentoCurrent.getSabores();

        // sabores.add(sabor);

        // adicionarSaborPizza(estabelecimentoCurrent, sabores);

        estabelecimentoCurrent.getSabores().add(sabor);

        saborRepository.flush();

        return sabor;
    }

    // private void adicionarSaborPizza(Estabelecimento estabelecimento, Set<Sabor> sabores) {
    //     estabelecimento.setSabores(sabores);
    // }
}
