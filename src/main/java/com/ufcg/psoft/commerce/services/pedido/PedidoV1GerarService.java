package com.ufcg.psoft.commerce.services.pedido;

import com.ufcg.psoft.commerce.dto.cliente.ClienteGetDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.model.Pedido;
import com.ufcg.psoft.commerce.model.PizzaGrande;
import com.ufcg.psoft.commerce.model.PizzaMedia;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.PizzaGrandeRepository;
import com.ufcg.psoft.commerce.repository.PizzaMediaRepository;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class PedidoV1GerarService implements PedidoGerarService {

    @Autowired
    SaborGetService saborGetService;

    @Autowired
    PizzaMediaRepository pizzaMediaRepository;

    @Autowired
    PizzaGrandeRepository pizzaGrandeRepository;

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

        for (PizzaPostPutDTO pizzaDTO: pedidoPostPutRequestDTO.getPizzas()) {
            if (pizzaDTO.getTamanho().equals("media")) {
                Sabor sabor = saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome());
                PizzaMedia pizzaMedia = gerarPizzaMedia(sabor);
                pizzasMedias.add(pizzaMedia);
                preco = preco.add(pizzaMedia.calculoDePreco());
            } else if (pizzaDTO.getTamanho().equals("grande")) {
                Set<Sabor> sabores = Set.of(
                        saborGetService.getSaborByNome(pizzaDTO.getSabor1().getNome()),
                        saborGetService.getSaborByNome(pizzaDTO.getSabor2().getNome())
                );
                PizzaGrande pizzaGrande = gerarPizzaGrande(sabores);
                pizzasGrandes.add(pizzaGrande);
                preco = preco.add(pizzaGrande.calculoDePreco());
            }
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
        PizzaMedia pizzaMedia = PizzaMedia.builder()
                .sabor(sabor)
                .build();
        return pizzaMediaRepository.save(pizzaMedia);
    }

    private PizzaGrande gerarPizzaGrande(Set<Sabor> sabores) {
        PizzaGrande pizzaGrande = PizzaGrande.builder()
                .sabores(sabores)
                .build();
        return pizzaGrandeRepository.save(pizzaGrande);
    }

    private String gerarEnderecoEntrega(PedidoPostPutRequestDTO pedidoPostPutRequestDTO, ClienteGetDTO cliente) {
        String enderecoEntrega = pedidoPostPutRequestDTO.getEnderecoEntrega();
        if (enderecoEntrega == null) enderecoEntrega = cliente.getEndereco();
        return enderecoEntrega;
    }
}
