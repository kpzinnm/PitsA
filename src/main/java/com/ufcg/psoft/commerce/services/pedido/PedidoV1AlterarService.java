package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoSemEntregadorNoMomentoException;
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
        Estabelecimento estabelecimento = estabelecimentoPegarService.pegarEstabelecimento(estabelecimentoId);
        estabelecimentoValid.validar(estabelecimentoId, estabelecimentoCodigoAcesso);

        if (Objects.equals(estabelecimento.getId(), pedidoFromDB.getEstabelecimentoId())) {
            List<Entregador> entregadores = estabelecimento.getEntregadoresDisponiveis().stream()
                    .collect(Collectors.toList());

            if (entregadores.size() > 0) {

                pedidoFromDB.setEntregadorId(entregadores.get(0).getId());
                pedidoFromDB.setStatusEntrega(pedidoPostPutRequestDTO.getStatusEntrega());

                pedidoRepository.flush();

                PedidoResponseDTO response = PedidoResponseDTO.builder()
                                    .id(pedidoFromDB.getId())
                                    .clienteId(pedidoFromDB.getClienteId())
                                    .entregadorId(pedidoFromDB.getEntregadorId())
                                    .estabelecimentoId(pedidoFromDB.getEstabelecimentoId())
                                    .statusEntrega(pedidoFromDB.getStatusEntrega())
                                    .build();

                return response;
            } else {
                throw new EstabelecimentoSemEntregadorNoMomentoException();
            }

        } else {
            throw new EstabelecimentoCodigoAcessoDiferenteException();
        }
    }
}
