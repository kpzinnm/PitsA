package com.ufcg.psoft.commerce.services.pedido;

import java.util.Objects;

import com.ufcg.psoft.commerce.exception.PedidoNotOnTheWayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;

@Service
public class ClienteV1ConfirmaEntrega implements ClienteConfirmaEntrega {

    @Autowired
    private PedidoGetService pedidoGetService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private PedidoNotificaStatusEventManager pedidoNotificaStatusEventManager;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Override
    public PedidoResponseDTO confirmaEntrega(Long pedidoId, Long clienteId, String clienteCodigoAcesso,
            PedidoPutRequestDTO pedidoPutRequestDTO) {

        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);

        if (Objects.equals(pedido.getStatus(), "Pedido em rota")) {
            ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);
            clienteValidaCodigoAcessoService.validaCodigoAcesso(clienteId, clienteCodigoAcesso);

            if (Objects.equals(pedido.getClienteId(), cliente.getId())) {
                pedido.setStatus(pedidoPutRequestDTO.getStatus());

                pedidoRepository.flush();

                PedidoResponseDTO response = PedidoResponseDTO.builder()
                        .id(pedido.getId())
                        .clienteId(pedido.getClienteId())
                        .entregadorId(pedido.getEntregadorId())
                        .estabelecimentoId(pedido.getEstabelecimentoId())
                        .status(pedido.getStatus())
                        .build();
                
                return response;
            } else throw new CodigoDeAcessoInvalidoException();
        } else throw new PedidoNotOnTheWayException();
    }
}
