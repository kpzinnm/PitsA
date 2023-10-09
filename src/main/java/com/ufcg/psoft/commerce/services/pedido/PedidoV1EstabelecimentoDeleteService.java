package com.ufcg.psoft.commerce.services.pedido;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;

@Service
public class PedidoV1EstabelecimentoDeleteService implements PedidoEstabelecimentoDeleteService {

    @Autowired
    PedidoGetService pedidoGetService;

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;

    @Autowired
    EstabelecimentoPegarService estabelecimentoBuscarService;

    @Autowired
    PedidoRepository pedidoRepository;

    @Override
    public void deletePedido(
            Long pedidoId,
            Long estabelecimentoId,
            String estabelecimentoCodigoAcesso,
            String codigoAcesso) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        estabelecimentoValidar.validar(estabelecimentoId, codigoAcesso);

        if (Objects.equals(pedido.getEstabelecimentoId(), estabelecimentoId)) {
            pedidoRepository.delete(pedido);
        } else throw new EstabelecimentoCodigoAcessoDiferenteException();

    }

    @Override
    public void deleteTodosPedidos(
            Long estabelecimentoId,
            String codigoAcesso) {

        estabelecimentoValidar.validar(estabelecimentoId, codigoAcesso);
        Estabelecimento estabelecimento = estabelecimentoBuscarService.pegarEstabelecimento(estabelecimentoId);
        
        if (Objects.equals(estabelecimento.getCodigoAcesso(), codigoAcesso)) {
            List<Pedido> pedidos = pedidoRepository.findAllByEstabelecimentoId(estabelecimentoId);

            pedidoRepository.deleteAll(pedidos);
        } else throw new EstabelecimentoCodigoAcessoDiferenteException();

        pedidoRepository.flush();
    }

}
