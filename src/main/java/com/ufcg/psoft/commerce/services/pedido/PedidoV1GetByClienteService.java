package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PedidoV1GetByClienteService implements PedidoGetByClienteService {

    @Autowired
    private PedidoGetService pedidoGetService;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Override
    public Pedido pegarPedido(
            Long pedidoId,
            Long clienteId,
            String clienteCodigoAcesso
    ) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        clienteValidaCodigoAcessoService.validaCodigoAcesso(clienteId, clienteCodigoAcesso);

        if (Objects.equals(pedido.getClienteId(), clienteId)) {
            return pedido;
        } else throw new CodigoDeAcessoInvalidoException();
    }
}
