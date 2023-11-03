package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;

public interface EntregadorAtualizarService {
    public EntregadorGetRequestDTO atualizaEntregador(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO, Long idEntregador, String codigoAcesso);

    public EntregadorGetRequestDTO atualizaDisponibilidade(Long idEntregador, String codigoAcesso, boolean diponibilidade);
}
