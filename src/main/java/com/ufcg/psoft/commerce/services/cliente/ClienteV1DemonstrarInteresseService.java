package com.ufcg.psoft.commerce.services.cliente;


import com.ufcg.psoft.commerce.exception.*;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;


@Service
public class ClienteV1DemonstrarInteresseService implements ClienteDemonstrarInteresseService {


    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private SaborRepository saborRepository;

    @Override
    public Sabor demonstraInteresse(Long saborId, Long clienteId, String codigoAcesso) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        if(!saborRepository.existsById(saborId)) throw new SaborNotExistException();

        if (Objects.equals(clienteId, cliente.getId())
                &&
                Objects.equals(cliente.getCodigoAcesso(), codigoAcesso)) {

            Sabor saborConsultado = saborRepository.findById(saborId).get();

            if(saborConsultado.isDisponivel())
                throw new SaborJaEstaDisponivelException();

            if(saborConsultado.getDisponivel() == null)
                saborConsultado.setClienteInteressados(new ArrayList<>());

            saborConsultado.getClienteInteressados().add(cliente);

            saborRepository.flush();

           return saborConsultado;

        } else {
            throw new CodigoDeAcessoInvalidoException();
        }

    }

}
