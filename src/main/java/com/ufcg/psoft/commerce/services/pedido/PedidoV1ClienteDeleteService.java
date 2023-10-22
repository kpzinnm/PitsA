package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.PedidoEmAndamentoException;
import com.ufcg.psoft.commerce.exception.PedidoJaEstaProntoException;
import com.ufcg.psoft.commerce.exception.PedidoNotExistException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1ClienteDeleteService implements PedidoClienteDeleteService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PedidoGetService pedidoGetService;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Override
    public void clienteDeletaPedido(
            Long pedidoId,
            Long clienteId,
            String clienteCodigoAcesso) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        if(verifyStatusPedidoEquals(pedido.getStatus(), "Pedido entregue")){
            clienteGetByIdService.getCliente(clienteId);
            clienteValidaCodigoAcessoService.validaCodigoAcesso(pedido.getClienteId(), clienteCodigoAcesso);
            pedidoRepository.delete(pedido);
        } else {
            throw new PedidoEmAndamentoException();
        }
    }

    @Override
    public void clienteDeleteTodosPedidosFeitos(Long clienteId) {
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);

        List<Pedido> pedidos = pedidoRepository.findAll();
        pedidos.stream().filter(pedido -> pedido.getClienteId().equals(cliente.getId()) && pedido.getStatus().equals("Pedido entregue")).toList();
        pedidoRepository.deleteAll(pedidos);

    }

    @Override
    public void cancelarPedido(Long pedidoId, String clienteCodigoAcesso) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(pedido.getClienteId());
        if (verifyClienteEquals(pedido.getClienteId(), cliente.getId())
                &&
                !verifyStatusPedidoEquals(pedido.getStatus(), "Pedido pronto")) {
            clienteValidaCodigoAcessoService.validaCodigoAcesso(pedido.getClienteId(), clienteCodigoAcesso);
            pedidoRepository.delete(pedido);
        } else if (verifyStatusPedidoEquals(pedido.getStatus(), "Pedido pronto")) {
            throw new PedidoJaEstaProntoException();
        }
    }

    private Boolean verifyClienteEquals(Long idClientePedido, Long idClientePassado) {
        return Objects.equals(idClientePedido, idClientePassado);
    }

    private Boolean verifyStatusPedidoEquals(String statusPedido, String statusVerify) {
        return Objects.equals(statusPedido, statusVerify);
    }
}
