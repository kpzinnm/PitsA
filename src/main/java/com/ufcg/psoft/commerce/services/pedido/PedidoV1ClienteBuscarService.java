package com.ufcg.psoft.commerce.services.pedido;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoDeAcessoInvalidoException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;

@Service
public class PedidoV1ClienteBuscarService implements PedidoClienteBuscarService {

    @Autowired
    private PedidoGetService pedidoGetService;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public PedidoResponseDTO buscarPedidoById(
            Long clienteId,
            Long estabelecimentoId,
            Long pedidoId,
            String clienteCodigoAcesso) {

        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        clienteGetByIdService.getCliente(clienteId);
        clienteValidaCodigoAcessoService.validaCodigoAcesso(clienteId, clienteCodigoAcesso);

        if (Objects.equals(estabelecimentoId, pedido.getEstabelecimentoId())
                && Objects.equals(clienteId, pedido.getClienteId())) {
            return modelMapper.map(pedido, PedidoResponseDTO.class);
        } else if (!Objects.equals(estabelecimentoId, pedido.getEstabelecimentoId())) {
            throw new EstabelecimentoNaoExisteException("O estabelecimento consultado nao existe!");
        } else
            throw new CodigoDeAcessoInvalidoException();
    }

    @Override
    public List<PedidoResponseDTO> buscarPedidos(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso) {

        ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);
        if (cliente == null)
            throw new ClienteNaoExisteException();

        List<Pedido> pedidoList = pedidoRepository.findAll();
        pedidoList.stream().filter(pedido -> pedido.getClienteId().equals(clienteId)
                && pedido.getEstabelecimentoId().equals(estabelecimentoId)).toList();

        List<PedidoResponseDTO> pedidosResponse = new ArrayList<PedidoResponseDTO>();

        for (Pedido pedido : pedidoList) {
            pedidosResponse.add(modelMapper.map(pedido, PedidoResponseDTO.class));
        }

        return pedidosResponse;
    }

    @Override
    public List<PedidoResponseDTO> buscarPedidoPorStatus(
            Long clienteId,
            Long estabelecimentoId,
            String status,
            String clienteCodigoAcesso
        ) {
       
        List<PedidoResponseDTO> pedidosResponse = this.buscarPedidos(clienteId, estabelecimentoId, clienteCodigoAcesso);
        pedidosResponse.stream().filter(pedido -> pedido.getStatus().equals(status)).toList();

        return pedidosResponse;
    }

    @Override
    public List<PedidoResponseDTO> buscarPedidosPorEntrega(Long clienteId, Long estabelecimentoId, String clienteCodigoAcesso) {
        return buscarPedidoPorStatus(clienteId, estabelecimentoId, "Pedido entregue", clienteCodigoAcesso);
    }

}
