package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.SaborIndisponivelNaoEncontradoException;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.ClienteInteresse;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.ClientesInteressadosRepository;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class ClienteV1DemonstrarInteresseService implements ClienteDemonstrarInteresseService {

    @Autowired
    private ClientesInteressadosRepository clientesInteressadosRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private SaborGetService saborGetService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SaborResponseDTO demonstraInteresse(Long saborId, Long clienteId, Long estabelecimentoId) {

        Cliente cliente = modelMapper.map(clienteGetByIdService.getCliente(clienteId), Cliente.class);

        ClienteInteresse clienteInteresse = new ClienteInteresse(cliente.getNome(), saborId);
        clientesInteressadosRepository.save(clienteInteresse);

        ClienteInteresseDTO clienteInteresseDTO = new ClienteInteresseDTO(cliente.getNome(), saborId);
        clientesInteressadosRepository.save(clienteInteresse);


        List<SaborResponseDTO> cardapio = saborGetService.getAllCardapio(estabelecimentoId);

        // Filtrar os sabores indisponíveis
        List<SaborResponseDTO> saboresIndisponiveis = cardapio.stream()
                .filter(sabor -> !sabor.isDisponivel())
                .collect(Collectors.toList());

        SaborResponseDTO saborIndisponivel = null;
        for (SaborResponseDTO sabor : saboresIndisponiveis) {
            if (sabor.getId().equals(saborId)) {
                saborIndisponivel = sabor;
                break;
            }
        }

        if (saborIndisponivel != null) {
            saborIndisponivel.getClientesInteressados().add(clienteInteresseDTO);
            return saborIndisponivel;
        } else {
            throw new SaborIndisponivelNaoEncontradoException();
        }
    }
}

