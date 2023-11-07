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
import com.ufcg.psoft.commerce.services.associacao.AssociacaoPegarService;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.entregador.EntregadorPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    ModelMapper modelMapper;
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
    private PedidoNotificaStatusEventManager pedidoNotificaStatusEventManager;

    @Autowired
    private PedidoGetByEstabelecimentoService pedidoGetByEstabelecimentoService;

    @Autowired
    private AssociacaoPegarService associacaoPegarService;

    @Autowired
    private EntregadorPegarService entregadorPegarService;

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
                Set<Entregador> entregadores = this.pegaEntregadoresDisponiveisDoEstabelecimento(estabelecimentoId);
                Entregador entregador = this.selecionarEntregadorMaisAntigo(
                        entregadores
                );

                if (entregador != null) {

                    pedidoFromDB.setEntregadorId(entregador.getId());
                    pedidoFromDB.setStatus(pedidoPostPutRequestDTO.getStatus());
                    pedidoFromDB.setAguardandoAssociarEntregador(false);

                    pedidoRepository.flush();

                    PedidoResponseDTO response = PedidoResponseDTO.builder()
                            .id(pedidoFromDB.getId())
                            .clienteId(pedidoFromDB.getClienteId())
                            .entregadorId(pedidoFromDB.getEntregadorId())
                            .estabelecimentoId(pedidoFromDB.getEstabelecimentoId())
                            .status(pedidoFromDB.getStatus())
                            .build();
                    ClienteGetDTO cliente = clienteGetByIdService.getCliente(pedidoFromDB.getClienteId());
                    pedidoNotificaStatusEventManager.notificaClienteStatusEntrega(cliente, entregador);
                    return response;
                } else {
                    pedidoNotificaStatusEventManager.notificaClienteEntregadorNaoDisponivel(cliente);
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
            this.associacaoEntregadorAutomatica(pedido);
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

    private void associacaoEntregadorAutomatica(Pedido pedido) {
        Estabelecimento estabelecimento = estabelecimentoPegarService.pegarEstabelecimento(
                pedido.getEstabelecimentoId()
        );

        Set<Entregador> entregadores = pegaEntregadoresDisponiveisDoEstabelecimento(pedido.getEstabelecimentoId());
        PedidoPutRequestDTO pedidoPutRequestDTO = modelMapper.map(pedido, PedidoPutRequestDTO.class);

        if (!entregadores.isEmpty()) {
            pedidoPutRequestDTO.setStatus("Pedido em rota");
            this.associarEntregador(
                    pedido.getId(),
                    pedido.getEstabelecimentoId(),
                    estabelecimento.getCodigoAcesso(),
                    pedidoPutRequestDTO);
        }
    }

    private Entregador selecionarEntregadorMaisAntigo(Set<Entregador> entregadores) {
        Entregador entregadorMaisAntigo = null;
        LocalDateTime horarioMaisAntigo = null;

        for (Entregador entregador : entregadores) {
            LocalDateTime horarioDisponibilidade = entregador.getHorarioDisponibilidade();

            if (horarioMaisAntigo == null || horarioDisponibilidade.isBefore(horarioMaisAntigo)) {
                horarioMaisAntigo = horarioDisponibilidade;
                entregadorMaisAntigo = entregador;
            }
        }

        return entregadorMaisAntigo;
    }

    private Set<Entregador> pegaEntregadoresDisponiveisDoEstabelecimento(Long estabelecimentoId) {
        Set<Long> entregadorIds = associacaoPegarService.pegarEntregadorIdsDoEstabelecimento(estabelecimentoId);
        Set<Entregador> entregadoresDisponiveis = new HashSet<>();
        for (Long id: entregadorIds) {
            Entregador entregadorFromDb = entregadorPegarService.pegaEntregadorObjeto(id);
            if (entregadorFromDb.isDisponibilidade()) {
                entregadoresDisponiveis.add(entregadorFromDb);
            }
        }
        return entregadoresDisponiveis;
    }
}
