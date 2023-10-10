package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;

public interface ClienteDemonstrarInteresseService {

    public SaborResponseDTO demonstraInteresse(Long saborId, Long clienteId, Long estabelecimentoId);
}
