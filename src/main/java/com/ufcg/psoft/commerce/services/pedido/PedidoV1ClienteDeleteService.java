package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.exception.PedidoNotExistException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1ClienteDeleteService implements PedidoClienteDeleteService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Override
    public void clienteDeletaPedido(
            Long pedidoId,
            Long clienteId,
            String clienteCodigoAcesso
    ) {
        if (pedidoRepository.existsById(pedidoId)) {
            Pedido pedido = pedidoRepository.findById(pedidoId).get();
            clienteGetByIdService.getCliente(clienteId);
            clienteValidaCodigoAcessoService.validaCodigoAcesso(pedido.getClienteId(), clienteCodigoAcesso);
            pedidoRepository.delete(pedido);
        } else {
            throw new PedidoNotExistException();
        }
    }
}
