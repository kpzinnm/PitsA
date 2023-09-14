package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1AtualzarService implements EntregadorAtualizarService{
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorValidaCodigoAcessoService entregadorValidaCodigoAcessoService;
    @Autowired
    EntregadorVerificaTipoVeiculo entregadorVerificaTipoVeiculo;

    @Override
    public EntregadorGetRequestDTO atualizaEntregador(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO,Long idEntregador, String codigoAcesso) {
        //Verifica se o entregador existe
        if(entregadorRepository.existsById(idEntregador)){
            //Verifica se o entregador tem o mesmo o codigo de acesso passado como parametro
            entregadorValidaCodigoAcessoService.validacodigoAcessoService(idEntregador, codigoAcesso);

            entregadorVerificaTipoVeiculo.verificaTipoVeiculo(entregadorPostPutRequestDTO);

            Entregador entregador = entregadorRepository.findById(idEntregador).get();

            entregador.setCodigoAcesso(entregadorPostPutRequestDTO.getCodigoAcesso());
            entregador.setNome(entregadorPostPutRequestDTO.getNome());
            entregador.setCorVeiculo(entregadorPostPutRequestDTO.getCorVeiculo());
            entregador.setTipoVeiculo(entregadorPostPutRequestDTO.getTipoVeiculo());
            entregador.setPlacaVeiculo(entregadorPostPutRequestDTO.getPlacaVeiculo());

            return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
        }else{
            throw new EntregadorNaoCadastradoException();
        }
    }
}
