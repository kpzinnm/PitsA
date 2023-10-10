package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;

@FunctionalInterface
public interface ClienteCriarService {

    public Cliente criarCliente(Cliente cliente);

   // public Cliente criarCliente(ClientePostPutRequestDTO cliente);

    }
