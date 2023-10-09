package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.PizzaGrandeRepository;
import com.ufcg.psoft.commerce.repository.PizzaMediaRepository;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PedidoV1GerarService implements PedidoGerarService {

    @Autowired
    private SaborGetService saborGetService;

    @Autowired
    private PizzaMediaRepository pizzaMediaRepository;

    @Autowired
    private PizzaGrandeRepository pizzaGrandeRepository;

    @Override
    public Pedido gerarPedido(
            PedidoPostPutRequestDTO pedidoPostPutRequestDTO,
            ClienteGetDTO cliente,
            Long estabelecimentoId
    ) {
        String enderecoEntrega = gerarEnderecoEntrega(pedidoPostPutRequestDTO, cliente);

        // Gerando a lista de pizzas
        List<PizzaMedia> pizzasMedias = new ArrayList<PizzaMedia>();
        List<PizzaGrande> pizzasGrandes = new ArrayList<PizzaGrande>();
        BigDecimal preco = new BigDecimal(0.0);
        HashMap<String, GerarPizzaFunction> strategyMap = new HashMap<>();

        strategyMap.put("media", (PizzaPostPutDTO pizzaDTO) -> {
            Sabor sabor = saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome());
            PizzaMedia pizzaMedia = gerarPizzaMedia(sabor);
            pizzasMedias.add(pizzaMedia);
            return pizzaMedia;
        });

        strategyMap.put("grande", (PizzaPostPutDTO pizzaDTO) -> {
            Set<Sabor> sabores = Set.of(
                    saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()),
                    saborGetService.getSaborByNome(pizzaDTO.getSabor2().getNome())
            );
            PizzaGrande pizzaGrande = gerarPizzaGrande(sabores);
            pizzasGrandes.add(pizzaGrande);
            return pizzaGrande;
        });

        for (PizzaPostPutDTO pizzaDTO: pedidoPostPutRequestDTO.getPizzas()) {
            GerarPizzaFunction strategy = strategyMap.get(pizzaDTO.getTamanho());
            PizzaInterface pizza = strategy.gerarPizza(pizzaDTO);
            preco = preco.add(pizza.calculoDePreco());
        }

        // Montando o Pedido

        return Pedido.builder()
                .preco(preco)
                .enderecoEntrega(enderecoEntrega)
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimentoId)
                .pizzasMedias(pizzasMedias)
                .pizzasGrandes(pizzasGrandes)
                .statusPagamento(false)
                .statusEntrega("")
                .build();
    }

    private PizzaMedia gerarPizzaMedia(Sabor sabor) {
        PizzaMedia pizza = PizzaMedia.builder()
                .sabor(sabor)
                .build();
        return this.pizzaMediaRepository.save(pizza);
    }

    private PizzaGrande gerarPizzaGrande(Set<Sabor> sabores) {
        PizzaGrande pizza = PizzaGrande.builder()
                .sabores(sabores)
                .build();
        return this.pizzaGrandeRepository.save(pizza);
    }

    private String gerarEnderecoEntrega(PedidoPostPutRequestDTO pedidoPostPutRequestDTO, ClienteGetDTO cliente) {
        String enderecoEntrega = pedidoPostPutRequestDTO.getEnderecoEntrega();
        if (enderecoEntrega == null) enderecoEntrega = cliente.getEndereco();
        return enderecoEntrega;
    }
}
