package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import org.springframework.stereotype.Service;

public interface EntregadorVerificaTipoVeiculo {
    public void verificaTipoVeiculo(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO);
}
