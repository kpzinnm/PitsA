package com.ufcg.psoft.commerce.service;

import com.ufcg.psoft.commerce.dto.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;

@FunctionalInterface
public interface ClienteAtualizarService {

    public Cliente atualizaCliente(ClientePostPutRequestDTO clientePostPutRequestDTO, Long id, String codigoAcesso);
}
