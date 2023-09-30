package com.ufcg.psoft.commerce.services.cliente;
import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClienteV1ValidaCodigoAcessoService implements ClienteValidaCodigoAcessoService{

    @Autowired
    ClienteRepository clienteRepository;

    @Override
    public Boolean validaCodigoAcesso(Long clienteId, String codigoAcesso) {
        Cliente cliente =  clienteRepository.findById(clienteId).get();

        if(!cliente.getCodigoAcesso().equals(codigoAcesso)){
            throw new CodigoDeAcessoInvalidoException();
        }
        return true;
    }
}
