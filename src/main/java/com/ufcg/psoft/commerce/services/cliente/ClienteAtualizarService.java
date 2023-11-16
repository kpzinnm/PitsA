package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;

@FunctionalInterface
public interface ClienteAtualizarService {

    public Cliente atualizaCliente(ClientePostPutRequestDTO clientePostPutRequestDTO, Long id, String codigoAcesso);
}
