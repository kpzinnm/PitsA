package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1CriarService implements ClienteCriarService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public Cliente criarCliente(Cliente cliente) {

        return clienteRepository.save(cliente);
    }

}
