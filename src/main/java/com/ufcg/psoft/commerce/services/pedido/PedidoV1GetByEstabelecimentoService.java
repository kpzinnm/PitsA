package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.exception.EstabelecimentoCodigoAcessoDiferenteException;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class PedidoV1GetByEstabelecimentoService implements PedidoGetByEstabelecimentoService {

    @Autowired
    PedidoGetService pedidoGetService;

    @Autowired
    EstabelecimentoValidar estabelecimentoValidar;

    @Override
    public Pedido pegarPedido(
            Long pedidoId,
            Long estabelecimentoId,
            String codigoAcesso
    ) {
        Pedido pedido = pedidoGetService.pegarPedido(pedidoId);
        estabelecimentoValidar.validar(estabelecimentoId, codigoAcesso);
        if (Objects.equals(pedido.getEstabelecimentoId(), estabelecimentoId)) {
            return pedido;
        } else throw new EstabelecimentoCodigoAcessoDiferenteException();
    }
}
