package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
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
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Override
    public void clienteDeletaPedido(
            Long pedidoId,
            Long clienteId,
            String clienteCodigoAcesso) {
        if (pedidoRepository.existsById(pedidoId)) {
            Pedido pedido = pedidoRepository.findById(pedidoId).get();
            clienteGetByIdService.getCliente(clienteId);
            clienteValidaCodigoAcessoService.validaCodigoAcesso(pedido.getClienteId(), clienteCodigoAcesso);
            pedidoRepository.delete(pedido);
        } else {
            throw new PedidoNotExistException();
        }
    }

    @Override
    public void clienteDeleteTodosPedidosFeitos(Long clienteId) {
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);
        if (cliente == null)
            throw new ClienteNaoExisteException();

        List<Pedido> pedidos = pedidoRepository.findAll();
        pedidos.stream().filter(pedido -> pedido.getClienteId().equals(clienteId)).toList();

        pedidoRepository.deleteAll(pedidos);

    }

    // Refatorar esse method ta muito misturado, acredito que já funciona dessa forma.

    @Override
    public void cancelarPedido(Long pedidoId, String clienteCodigoAcesso) {
        if (pedidoRepository.existsById(pedidoId)) {
            Pedido pedido = pedidoRepository.findById(pedidoId).get();
            ClienteGetDTO cliente = clienteGetByIdService.getCliente(pedido.getClienteId());
            if (cliente == null)
                throw new ClienteNaoExisteException();
            if (Objects.equals(pedido.getClienteId(), cliente.getId())
                    &&
                    !Objects.equals(pedido.getStatus(), "Pedido pronto")) {
                clienteValidaCodigoAcessoService.validaCodigoAcesso(pedido.getClienteId(), clienteCodigoAcesso);
                pedidoRepository.delete(pedido);
            } else if (Objects.equals(pedido.getStatus(), "Pedido pronto")){
                throw new PedidoJaEstaProntoException();
            }
        } else
            throw new PedidoNotExistException();
    }
}
