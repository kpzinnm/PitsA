package com.ufcg.psoft.commerce.service;

import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;

@Service
public class ClienteV1RemoverService implements ClienteRemoverService{

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public void removeCliente(Long id, String codigoAcesso) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(ClienteNaoExisteException::new);

        if(codigoAcesso != null && codigoAcesso.equals(cliente.getCodigoAcesso())){
            clienteRepository.delete(cliente);
        } else{
            throw new CodigoDeAcessoInvalidoException();
        }
    }
}
