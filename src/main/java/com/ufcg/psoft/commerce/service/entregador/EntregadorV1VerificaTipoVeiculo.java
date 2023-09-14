package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EntregadorTipoVeiculoInvalido;
import com.ufcg.psoft.commerce.repository.entregador.EntregadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EntregadorV1VerificaTipoVeiculo implements EntregadorVerificaTipoVeiculo{
    @Override
    public void verificaTipoVeiculo(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO) {
        String tipoVeiculo = entregadorPostPutRequestDTO.getTipoVeiculo();
        if(!(tipoVeiculo.equals("moto") || tipoVeiculo.equals("carro")) ){
            throw new EntregadorTipoVeiculoInvalido();
        }
    }
}
