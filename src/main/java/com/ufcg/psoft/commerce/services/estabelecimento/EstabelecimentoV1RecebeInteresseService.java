package com.ufcg.psoft.commerce.services.estabelecimento;

import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.model.ClienteInteresse;
import com.ufcg.psoft.commerce.repository.ClientesInteressadosRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EstabelecimentoV1RecebeInteresseService implements EstabelecimentoRecebeInteresseService {

    @Autowired
    private ClientesInteressadosRepository clientesInteressadosRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private SaborGetService saborGetService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void recebeInteresse(Long saborId, Long clienteId, Long estabelecimentoId) {
        /*Cliente cliente = modelMapper.map(clienteGetByIdService.getCliente(clienteId), Cliente.class);
        ClienteInteresse clienteInteresse = new ClienteInteresse(cliente.getNome(), saborId);
        clientesInteressadosRepository.save(clienteInteresse);*/
    }


}
