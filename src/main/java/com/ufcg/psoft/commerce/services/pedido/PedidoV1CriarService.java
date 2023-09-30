package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.repository.PizzaRepository;
import com.ufcg.psoft.commerce.repository.SaborRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoBuscarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoValidar;
import com.ufcg.psoft.commerce.services.sabor.SaborCadastraService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import com.ufcg.psoft.commerce.services.sabor.SaborVerificaTipoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoV1CriarService implements PedidoCriarService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    ClienteGetByIdService clienteGetByIdService;

    @Autowired
    ClienteValidaCodigoAcessoService clienteValidaCodigoAcessoService;

    @Autowired
    EstabelecimentoPegarService estabelecimentoPegarService;

    @Autowired
    SaborGetService saborGetService;

    @Autowired
    PizzaRepository pizzaRepository;

    @Override
    public Pedido criarPedido(
            Long clienteId,
            String clienteCodigoAcesso,
            Long estabelecimentoId,
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO
    ) {
        // Validação do cliente, codigo de acesso e estabelecimento
        ClienteGetDTO cliente = clienteGetByIdService.getCliente(clienteId);
        if (cliente == null) throw new ClienteNaoExisteException();
        clienteValidaCodigoAcessoService.validaCodigoAcesso(clienteId, clienteCodigoAcesso);
        if (
                estabelecimentoPegarService.pegarEstabelecimento(estabelecimentoId) == null
        ) throw new EstabelecimentoNaoExisteException();

        // Montando a pizza

        String enderecoEntrega = pedidoPostPutRequestDTO.getEnderecoEntrega();
        if (enderecoEntrega == null) enderecoEntrega = cliente.getEndereco();

        List<Pizza> pizzas = new ArrayList<Pizza>();
        BigDecimal preco = new BigDecimal(0.0);

        for (PizzaPostPutDTO pizzaDTO: pedidoPostPutRequestDTO.getPizzas()) {
            if (pizzaDTO.getTamanho().equals("media")) {
                Pizza pizza = Pizza.builder()
                        .sabor1(saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()))
                        .tamanho(pizzaDTO.getTamanho())
                        .build();
                pizzaRepository.save(pizza);
                pizzas.add(pizza);
                preco = preco.add(pizza.getSabor1().getPrecoM());
            } else {
                if (pizzaDTO.getSabor2() == null) pizzaDTO.setSabor2(pizzaDTO.getSabor1());
                Pizza pizza = Pizza.builder()
                        .sabor1(saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()))
                        .sabor2(saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()))
                        .tamanho(pizzaDTO.getTamanho())
                        .build();
                pizzaRepository.save(pizza);
                pizzas.add(pizza);
                BigDecimal total = pizza.getSabor1().getPrecoG()
                        .add(pizza.getSabor2().getPrecoG())
                        .divide(new BigDecimal(2.0));
                preco = preco.add(total);
            }
        }

        // Montando o Pedido
        Pedido pedido = Pedido.builder()
                .preco(preco)
                .enderecoEntrega(enderecoEntrega)
                .clienteId(clienteId)
                .estabelecimentoId(estabelecimentoId)
                .pizzas(pizzas)
                .build();
        return pedidoRepository.save(pedido);
    }
}
