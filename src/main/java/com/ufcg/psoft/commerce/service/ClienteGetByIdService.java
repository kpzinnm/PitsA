package com.ufcg.psoft.commerce.service;

import com.ufcg.psoft.commerce.dto.ClienteGetDTO;

@FunctionalInterface
public interface ClienteGetByIdService {

    public ClienteGetDTO getCliente(Long id);
}
