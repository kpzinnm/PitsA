package com.ufcg.psoft.commerce.service;

import com.ufcg.psoft.commerce.dto.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1AtualizarService implements ClienteAtualizarService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Cliente atualizaCliente(ClientePostPutRequestDTO clientePostPutRequestDTO, Long id, String codigoAcesso) {
        Cliente cliente =  clienteRepository.findById(id)
                .orElseThrow(ClienteNaoExisteException::new);

        if(!cliente.getCodigoAcesso().equals(codigoAcesso) ||
                !clientePostPutRequestDTO.getCodigoAcesso().equals(codigoAcesso)){

            throw new CodigoDeAcessoInvalidoException();
        }

        cliente.setNome(clientePostPutRequestDTO.getNome());
        cliente.setEndereco(clientePostPutRequestDTO.getEndereco());
        cliente.setCodigoAcesso(clientePostPutRequestDTO.getCodigoAcesso());

        clienteRepository.save(cliente);

        return cliente;
    }
}
