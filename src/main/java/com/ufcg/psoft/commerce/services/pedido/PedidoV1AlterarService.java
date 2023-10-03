package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.repository.PizzaGrandeRepository;
import com.ufcg.psoft.commerce.repository.PizzaMediaRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PedidoV1AlterarService implements PedidoAlterarService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    ClienteGetByIdService clienteGetByIdService;

    @Autowired
    ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    EstabelecimentoPegarService estabelecimentoPegarService;

    @Autowired
    PedidoGetService pedidoGetService;

    @Autowired
    PedidoGerarService pedidoGerarService;

    @Override
    public Pedido alterarPedido(
            String clienteCodigoAcesso,
            Long pedidoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    ) {
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
}
