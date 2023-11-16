package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;

import java.util.List;

public interface EntregadorPegarService {
    public EntregadorGetRequestDTO pegaEntregador(Long idEndereco);
    public List<EntregadorGetRequestDTO> PegaTodosEntregadores();

    public Entregador pegaEntregadorObjeto(Long id);
}
