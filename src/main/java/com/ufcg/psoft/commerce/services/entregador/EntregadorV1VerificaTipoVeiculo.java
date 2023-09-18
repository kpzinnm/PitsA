package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EntregadorTipoVeiculoInvalido;
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
