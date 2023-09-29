package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;

@FunctionalInterface
public interface ClienteGetByIdService {

    public ClienteGetDTO getCliente(Long id);
}
