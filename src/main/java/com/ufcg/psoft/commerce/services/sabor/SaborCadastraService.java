package com.ufcg.psoft.commerce.services.sabor;

import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborCadastraService {

    public Sabor cadastrarSabor(SaborPostPutRequestDTO saborPostPutRequestDTO, Long estabelecimentoId, String estabelecimentoCodigoAcesso);
}
