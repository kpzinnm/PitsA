package com.ufcg.psoft.commerce.service.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;

import java.util.List;

public interface EntregadorPegarService {
    public EntregadorGetRequestDTO PegaEntregador(Long idEndereco);
    public List<EntregadorGetRequestDTO> PegaTodosEntregadores();
}
