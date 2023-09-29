package com.ufcg.psoft.commerce.services.cliente;

import java.util.List;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;

@FunctionalInterface
public interface ClienteLerTodosService {

    public List<ClienteGetDTO> getAllClientes();
}
