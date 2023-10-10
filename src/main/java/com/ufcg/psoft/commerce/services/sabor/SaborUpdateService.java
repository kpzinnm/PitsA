package com.ufcg.psoft.commerce.services.sabor;

import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;

public interface SaborUpdateService {
    public Sabor updateById(SaborPostPutRequestDTO saborPostPutRequestDTO, Long saborId, Long estabelecimentoId, String estabelecimentoCodigoAcesso);

    public Sabor updateByIdDisponibilidade(Long saborId, Long estabelecimentoId,
    String estabelecimentoCodigoAcesso, Boolean disponibilidade);
}
