package com.ufcg.psoft.commerce.service;

import java.util.List;

import com.ufcg.psoft.commerce.dto.ClienteGetDTO;
import com.ufcg.psoft.commerce.model.Cliente;

@FunctionalInterface
public interface ClienteLerTodosService {

    public List<ClienteGetDTO> getAllClientes();
}
