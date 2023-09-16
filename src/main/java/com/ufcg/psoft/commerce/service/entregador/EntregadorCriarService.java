package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;

public interface EntregadorCriarService {
    public Entregador criaEntregador(EntregadorPostPutRequestDTO entregadorPostPutRequestDto);
}
