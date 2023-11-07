package com.ufcg.psoft.commerce.services.entregador;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.exception.EntregadorNaoCadastradoException;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.EntregadorRepository;
import com.ufcg.psoft.commerce.services.associacao.AssociacaoPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.pedido.PedidoAlterarService;
import com.ufcg.psoft.commerce.services.pedido.PedidoGetAllByEstabelecimentoService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EntregadorV1AtualzarService implements EntregadorAtualizarService{
    @Autowired
    EntregadorRepository entregadorRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    EntregadorValidaCodigoAcessoService entregadorValidaCodigoAcessoService;
    @Autowired
    EntregadorVerificaTipoVeiculo entregadorVerificaTipoVeiculo;

    @Autowired
    AssociacaoPegarService associacaoPegarService;

    @Autowired
    PedidoGetAllByEstabelecimentoService pedidoGetAllByEstabelecimentoService;

    @Autowired
    PedidoAlterarService pedidoAlterarService;

    @Autowired
    EstabelecimentoPegarService estabelecimentoPegarService;

    @Override
    public EntregadorGetRequestDTO atualizaEntregador(EntregadorPostPutRequestDTO entregadorPostPutRequestDTO,Long idEntregador, String codigoAcesso) {
        //Verifica se o entregador existe
        if(entregadorRepository.existsById(idEntregador)){
            //Verifica se o entregador tem o mesmo o codigo de acesso passado como parametro
            entregadorValidaCodigoAcessoService.validacodigoAcessoService(idEntregador, codigoAcesso);

            entregadorVerificaTipoVeiculo.verificaTipoVeiculo(entregadorPostPutRequestDTO);

            Entregador entregador = entregadorRepository.findById(idEntregador).get();

            entregador.setCodigoAcesso(entregadorPostPutRequestDTO.getCodigoAcesso());
            entregador.setNome(entregadorPostPutRequestDTO.getNome());
            entregador.setCorVeiculo(entregadorPostPutRequestDTO.getCorVeiculo());
            entregador.setTipoVeiculo(entregadorPostPutRequestDTO.getTipoVeiculo());
            entregador.setPlacaVeiculo(entregadorPostPutRequestDTO.getPlacaVeiculo());

            return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
        }else{
            throw new EntregadorNaoCadastradoException();
        }
    }

    @Override
    public EntregadorGetRequestDTO atualizaDisponibilidade(Long idEntregador, String codigoAcesso, boolean diponibilidade) {
        if(entregadorRepository.existsById(idEntregador)){
            entregadorValidaCodigoAcessoService.validacodigoAcessoService(idEntregador, codigoAcesso);

            Entregador entregador = entregadorRepository.findById(idEntregador).get();

            entregador.setDisponibilidade(diponibilidade);

            atribuiAutomaticamenteSeHaPedidoPronto(entregador);

            entregadorRepository.flush();

            return modelMapper.map(entregador, EntregadorGetRequestDTO.class);
        }
        throw new EntregadorNaoCadastradoException();

    }

    private void atribuiAutomaticamenteSeHaPedidoPronto(Entregador entregador) {
        Set<Long> estabelecimentosIds = associacaoPegarService.pegarEstabelecimentoIdsDoEntregador(entregador.getId());
        for (Long id: estabelecimentosIds) {
            List<Pedido> listaPedidos = pedidoGetAllByEstabelecimentoService.listarPedidosEstabelecimento(id);
            for (Pedido pedido: listaPedidos) {
                if (pedido.getStatus().equals("Pedido pronto") && pedido.isAguardandoAssociarEntregador()) {
                    Estabelecimento estabelecimento = estabelecimentoPegarService.pegarEstabelecimento(id);
                    Pedido primeiroPedido = listaPedidos.get(0);
                    PedidoPutRequestDTO pedidoDTO = modelMapper.map(primeiroPedido, PedidoPutRequestDTO.class);
                    pedidoDTO.setStatus("Pedido em rota");
                    pedidoAlterarService.associarEntregador(
                            primeiroPedido.getId(),
                            primeiroPedido.getEstabelecimentoId(),
                            estabelecimento.getCodigoAcesso(),
                            pedidoDTO
                    );
                    return;
                }
            }
        }
    }
}
