package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;

public interface EntregadorAtualizarService {
    public EntregadorGetRequestDTO atualizaEntregador(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO, Long idEntregador, String codigoAcesso);
}
