package com.ufcg.psoft.commerce.services.cliente;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.cliente.ClienteInteresseDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.SaborIndisponivelNaoEncontradoException;
import com.ufcg.psoft.commerce.exception.SaborJaEstaDisponivelException;
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
    public Sabor demonstraInteresse(Long saborId, Long clienteId, String codigoAcesso, Long estabelecimentoId) {

        Cliente cliente = clienteRepository.findById(clienteId).orElseThrow(ClienteNaoExisteException::new);

        if (Objects.equals(clienteId, cliente.getId())
                &&
                Objects.equals(cliente.getCodigoAcesso(), codigoAcesso)) {

            List<Sabor> sabores = saborGetService.getAllCardapioSabor(estabelecimentoId);
            Sabor saborConsultado = new Sabor();

            for (Sabor sabor : sabores) {
                if (sabor.getId().equals(saborId)) {
                    saborConsultado = sabor;
                }
            }

            //ClienteInteresse clienteInteresse = new ClienteInteresse(cliente.getNome(), saborConsultado);
            //saborConsultado.iniciarInteresse();
            if(saborConsultado.getDisponivel() == null)
                saborConsultado.setClienteInteressados(new ArrayList<>());

            saborConsultado.getClienteInteressados().add(cliente);

            saborRepository.flush();

            //clientesInteressadosRepository.save(clienteInteresse);

            /*List<Estabelecimento> estabelecimentos = estabelecimentoBuscarService.listarTodos();

            Set<Sabor> sabores = new HashSet<Sabor>();

            for (Estabelecimento estabelecimento : estabelecimentos) {
                sabores.addAll(estabelecimento.getSabores());
            }
            List<Sabor> saboresReal = sabores.stream().filter(sabor -> sabor.getId().equals(saborId))
                    .collect(Collectors.toList());

            SaborResponseDTO saborResponseDTO = null;*/

            /*if (saboresReal.size() == 1) {

                saborResponseDTO = modelMapper.map(saboresReal.get(0), SaborResponseDTO.class);

                Boolean saborEstaIndisponivel = saborResponseDTO.isDisponivel();

                if (!saborEstaIndisponivel) {
                    throw new SaborJaEstaDisponivelException();
                }

                saborResponseDTO.getClientesInteressados().add(clienteInteresse);

                clienteRepository.flush();

            }*/

            /*SaborResponseDTO saborResponseDTO = SaborResponseDTO.builder()
                    .id(saborConsultado.getId())
                    .tipo(saborConsultado.getTipo())
                    .nome(saborConsultado.getNome())
                    .precoG(saborConsultado.getPrecoG())
                    .precoM(saborConsultado.getPrecoM())
                    .disponivel(saborConsultado.getDisponivel())
                    .clientesInteressados().build();*/
           return saborConsultado;
        } else {
            throw new SaborIndisponivelNaoEncontradoException();
        }

    }

    }

