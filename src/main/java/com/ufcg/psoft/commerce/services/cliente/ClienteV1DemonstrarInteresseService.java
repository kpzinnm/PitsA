package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.*;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.ClienteInteresse;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.ClienteRepository;
import com.ufcg.psoft.commerce.repository.ClientesInteressadosRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoBuscarService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ClienteV1DemonstrarInteresseService implements ClienteDemonstrarInteresseService {

    @Autowired
    private ClientesInteressadosRepository clientesInteressadosRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private SaborGetService saborGetService;

    @Autowired
    private SaborRepository saborRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Sabor demonstraInteresse(Long saborId, Long clienteId, String codigoAcesso) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);
        if(!saborRepository.existsById(saborId)) throw new SaborNotExistException();

        if (Objects.equals(clienteId, cliente.getId())
                &&
                Objects.equals(cliente.getCodigoAcesso(), codigoAcesso)) {

            List<Sabor> sabores = saborRepository.findAll();

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

