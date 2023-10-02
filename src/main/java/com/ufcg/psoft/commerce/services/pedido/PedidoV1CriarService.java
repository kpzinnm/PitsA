package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.EstabelecimentoNaoExisteException;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.PedidoRepository;
import com.ufcg.psoft.commerce.repository.PizzaGrandeRepository;
import com.ufcg.psoft.commerce.repository.PizzaMediaRepository;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteValidaCodigoAcessoService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoPegarService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    PizzaGrandeRepository pizzaGrandeRepository;

    @Autowired
    PizzaMediaRepository pizzaMediaRepository;

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

        List<PizzaMedia> pizzasMedias = new ArrayList<PizzaMedia>();
        List<PizzaGrande> pizzasGrandes = new ArrayList<PizzaGrande>();
        BigDecimal preco = new BigDecimal(0.0);

        for (PizzaPostPutDTO pizzaDTO: pedidoPostPutRequestDTO.getPizzas()) {
            if (pizzaDTO.getTamanho().equals("media")) {
                PizzaMedia pizzaMedia = PizzaMedia.builder()
                        .sabor(saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()))
                        .build();
                pizzaMediaRepository.save(pizzaMedia);
                pizzasMedias.add(pizzaMedia);
                preco = preco.add(pizzaMedia.calculoDePreco());
            } else if (pizzaDTO.getTamanho().equals("grande")) {
                PizzaGrande pizzaGrande = PizzaGrande.builder()
                        .sabores(Set.of(
                                saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()),
                                saborGetService.getSaborByNome(pizzaDTO.getSabor2().getNome())
                        ))
                        .build();
                pizzaGrandeRepository.save(pizzaGrande);
                pizzasGrandes.add(pizzaGrande);
                preco = preco.add(pizzaGrande.calculoDePreco());
            }
        }

        // Montando o Pedido
        Pedido pedido = Pedido.builder()
                .preco(preco)
                .enderecoEntrega(enderecoEntrega)
                .clienteId(clienteId)
                .estabelecimentoId(estabelecimentoId)
                .pizzasMedias(pizzasMedias)
                .pizzasGrandes(pizzasGrandes)
                .build();
        return pedidoRepository.save(pedido);
    }
}
