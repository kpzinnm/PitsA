package com.ufcg.psoft.commerce.service;

import com.ufcg.psoft.commerce.model.Cliente;

@FunctionalInterface
public interface ClienteCriarService {

    public Cliente criarCliente(Cliente cliente);
}
