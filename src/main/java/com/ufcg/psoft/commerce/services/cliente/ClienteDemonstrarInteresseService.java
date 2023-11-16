package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.model.Sabor;

public interface ClienteDemonstrarInteresseService {

    public Sabor demonstraInteresse(Long saborId, Long clienteId, String codigoAcesso);

}
