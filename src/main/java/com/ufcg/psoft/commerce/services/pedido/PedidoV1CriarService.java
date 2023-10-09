package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1CriarService implements PedidoCriarService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    ClienteGetByIdService clienteGetByIdService;

    @Autowired
    ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    EstabelecimentoPegarService estabelecimentoPegarService;

    @Autowired
    PedidoGerarService pedidoGerarService;

    @Override
    public Pedido criarPedido(
            Long clienteId,
            String clienteCodigoAcesso,
            Long estabelecimentoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    ) {
        // Validação do cliente, codigo de acesso e estabelecimento
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);
        if (cliente == null) throw new ClienteNaoExisteException();
        clienteValidaCodigoAcessoService.validaCodigoAcesso(clienteId, clienteCodigoAcesso);
        if (
                estabelecimentoPegarService.pegarEstabelecimento(estabelecimentoId) == null
        ) throw new EstabelecimentoNaoExisteException();

        return pedidoRepository.save(pedidoGerarService.gerarPedido(
                pedidoPostPutRequestDTO,
                cliente,
                estabelecimentoId
        ));
    }
}
