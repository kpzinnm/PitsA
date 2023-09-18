package com.ufcg.psoft.commerce.services.sabor;

import com.ufcg.psoft.commerce.model.Sabor;

@FunctionalInterface
public interface SaborCadastraService {

    public Sabor cadastrarSabor(Sabor sabor, Long estabelecimentoId, String estabelecimentoCodigoAcesso);
}
