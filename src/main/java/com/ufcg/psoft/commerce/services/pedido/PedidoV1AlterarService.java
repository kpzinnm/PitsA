package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoSemEntregadorNoMomentoException;
import com.ufcg.psoft.commerce.exception.PedidoNotPreparedException;
import com.ufcg.psoft.commerce.exception.PedidoNotReadyException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteGetByIdService clienteGetByIdService;

    @Autowired
    private ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    private EstabelecimentoPegarService estabelecimentoPegarService;

    @Autowired
    private PedidoGetService pedidoGetService;

    @Autowired
    private PedidoGerarService pedidoGerarService;

    @Autowired
    private EstabelecimentoValidar estabelecimentoValid;

    @Autowired
    private PedidoGetByEstabelecimentoService pedidoGetByEstabelecimentoService;

    @Override
    public Pedido alterarPedido(
            String clienteCodigoAcesso,
            Long pedidoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
        // Validação do cliente, codigo de acesso e estabelecimento
        Pedido pedidoFromDB = pedidoGetService.pegarPedido(pedidoId);
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(pedidoFromDB.getClienteId());
        clienteValidaCodigoAcessoService.validaCodigoAcesso(pedidoFromDB.getClienteId(), clienteCodigoAcesso);

        Pedido newPedido = pedidoGerarService.gerarPedido(
                pedidoPostPutRequestDTO,
                cliente,
                pedidoFromDB.getEstabelecimentoId());

        pedidoFromDB.setPreco(newPedido.getPreco());
        pedidoFromDB.setEnderecoEntrega(newPedido.getEnderecoEntrega());
        pedidoFromDB.setPizzasMedias(newPedido.getPizzasMedias());
        pedidoFromDB.setPizzasGrandes(newPedido.getPizzasGrandes());

        return pedidoRepository.save(pedidoFromDB);
    }

    @Override
    public PedidoResponseDTO associarEntregador(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso,
            PedidoPutRequestDTO pedidoPostPutRequestDTO) {
        Pedido pedidoFromDB = pedidoGetService.pegarPedido(pedidoId);
        if (Objects.equals(pedidoFromDB.getStatus(), "Pedido pronto")) {
            Estabelecimento estabelecimento = estabelecimentoPegarService.pegarEstabelecimento(estabelecimentoId);
            estabelecimentoValid.validar(estabelecimentoId, estabelecimentoCodigoAcesso);

            if (Objects.equals(estabelecimento.getId(), pedidoFromDB.getEstabelecimentoId())) {
                List<Entregador> entregadores = estabelecimento.getEntregadoresDisponiveis().stream()
                        .collect(Collectors.toList());

                if (entregadores.size() > 0) {

                    pedidoFromDB.setEntregadorId(entregadores.get(0).getId());
                    pedidoFromDB.setStatus(pedidoPostPutRequestDTO.getStatus());

                    pedidoRepository.flush();

                    PedidoResponseDTO response = PedidoResponseDTO.builder()
                            .id(pedidoFromDB.getId())
                            .clienteId(pedidoFromDB.getClienteId())
                            .entregadorId(pedidoFromDB.getEntregadorId())
                            .estabelecimentoId(pedidoFromDB.getEstabelecimentoId())
                            .status(pedidoFromDB.getStatus())
                            .build();

                    return response;
                } else {
                    throw new EstabelecimentoSemEntregadorNoMomentoException();
                }

            } else {
                throw new EstabelecimentoCodigoAcessoDiferenteException();
            }
        } else {
            throw new PedidoNotReadyException();
        }

    }

    @Override
    public PedidoResponseDTO setPedidoPronto(Long pedidoId, Long estabelecimentoId, String estabelecimentoCodigoAcesso) {
        Pedido pedido = pedidoGetByEstabelecimentoService.pegarPedido(
                pedidoId,
                estabelecimentoId,
                estabelecimentoCodigoAcesso
        );

        if (Objects.equals(pedido.getStatus(), "Pedido em preparo")) {
            pedido.setStatus("Pedido pronto");
            pedidoRepository.flush();
            return PedidoResponseDTO.builder()
                    .id(pedido.getId())
                    .clienteId(pedido.getClienteId())
                    .entregadorId(pedido.getEntregadorId())
                    .estabelecimentoId(pedido.getEstabelecimentoId())
                    .status(pedido.getStatus())
                    .build();
        } else {
            throw new PedidoNotPreparedException();
        }
    }
}
