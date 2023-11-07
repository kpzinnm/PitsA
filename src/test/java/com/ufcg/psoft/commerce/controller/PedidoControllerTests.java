package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.dto.pizza.PizzaPostPutDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.*;
import com.ufcg.psoft.commerce.repository.*;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
public class PedidoControllerTests {
    final String URI_PEDIDOS = "/api/v1/pedidos";

    @Autowired
    MockMvc driver;

    @Autowired
    PedidoRepository pedidoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;
    @Autowired
    SaborRepository saborRepository;
    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    PizzaMediaRepository pizzaMediaRepository;

    @Autowired
    PizzaGrandeRepository pizzaGrandeRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    ModelMapper modelMapper;

    ObjectMapper objectMapper = new ObjectMapper();
    Cliente cliente;
    Entregador entregador;
    Associacao associacao;
    Sabor sabor1;
    Sabor sabor2;
    PizzaMedia pizzaMedia;
    PizzaGrande pizzaGrande;
    Estabelecimento estabelecimento;
    Pedido pedido;
    Pedido pedido1;
    PedidoPostPutRequestDTO pedidoPostPutRequestDTO;
    PedidoPutRequestDTO pedidoPutRequestDTO;
    PedidoPutRequestDTO pedidoPutEntregueRequestDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("654321")
                .build());
        sabor1 = saborRepository.save(Sabor.builder()
                .nome("Sabor Um")
                .tipo("salgado")
                .precoM(new BigDecimal(10.0))
                .precoG(new BigDecimal(20.0))
                .disponivel(true)
                .build());
        sabor2 = saborRepository.save(Sabor.builder()
                .nome("Sabor Dois")
                .tipo("doce")
                .precoM(new BigDecimal(15.0))
                .precoG(new BigDecimal(30.0))
                .disponivel(true)
                .build());
        cliente = clienteRepository.save(Cliente.builder()
                .nome("Anton Ego")
                .endereco("Paris")
                .codigoAcesso("123456")
                .build());
        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Joãozinho")
                .placaVeiculo("ABC-1234")
                .corVeiculo("Azul")
                .tipoVeiculo("Moto")
                .codigoAcesso("101010")
                .build());
        associacao = associacaoRepository.save(
                Associacao.builder()
                        .estabelecimentoId(estabelecimento.getId())
                        .entregadorId(entregador.getId())
                        .status(true)
                .build()
        );
        pizzaMedia = PizzaMedia.builder()
                .sabor(sabor1)
                .build();
        pizzaGrande = PizzaGrande.builder()
                .sabores(Set.of(sabor1, sabor2))
                .build();
        pedido = Pedido.builder()
                .preco(new BigDecimal(10.0))
                .enderecoEntrega("Casa 237")
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimento.getId())
                .entregadorId(entregador.getId())
                .pizzasMedias(List.of(pizzaMedia))
                .pizzasGrandes(List.of())
                .statusPagamento(false)
                .status("Pedido recebido")
                .aguardandoAssociarEntregador(true)
                .build();
        pedido1 = Pedido.builder()
                .preco(new BigDecimal(35.0))
                .enderecoEntrega("Casa 237")
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimento.getId())
                .entregadorId(entregador.getId())
                .pizzasMedias(List.of(pizzaMedia))
                .pizzasGrandes(List.of(pizzaGrande))
                .statusPagamento(false)
                .status("Pedido recebido")
                .aguardandoAssociarEntregador(true)
                .build();
        pedidoPutRequestDTO = PedidoPutRequestDTO.builder()
                .status("Pedido em rota")
                .build();
        pedidoPutEntregueRequestDTO = PedidoPutRequestDTO.builder()
                .status("Pedido entregue")
                .build();
        pedidoPostPutRequestDTO = PedidoPostPutRequestDTO.builder()
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .pizzas(List.of(PizzaPostPutDTO.builder()
                        .sabor1(modelMapper.map(sabor1, SaborPostPutRequestDTO.class))
                        .tamanho("media")
                        .build()))
                .build();
    }

    @AfterEach
    void tearDown() {
        clienteRepository.deleteAll();
        estabelecimentoRepository.deleteAll();
        pedidoRepository.deleteAll();
        pizzaGrandeRepository.deleteAll();
        pizzaMediaRepository.deleteAll();
        saborRepository.deleteAll();
        entregadorRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
    class PedidoVerificacaoFluxosBasicosApiRest {

        @Test
        @DisplayName("Quando criamos um novo pedido com dados válidos")
        void quandoCriamosUmNovoPedidoComDadosValidos() throws Exception {
            // Arrange
            String pedidoStatusInicial = "Pedido recebido";

            // Act
            String responseJsonString = driver.perform(post(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteId", cliente.getId().toString())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .param("estabelecimentoId", estabelecimento.getId().toString())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())// Codigo 201
                    .andReturn().getResponse().getContentAsString();

            Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.PedidoBuilder.class)
                    .build();

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(),
                            resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(),
                            modelMapper.map(resultado.getPizzasMedias().get(0).getSabor(),
                                    SaborPostPutRequestDTO.class)),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(),
                            resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco()),
                    () -> assertFalse(resultado.getStatusPagamento()),
                    () -> assertEquals(pedidoStatusInicial, resultado.getStatus()));
        }

        @Test
        @DisplayName("Quando alteramos um novo pedido com dados válidos")
        void quandoAlteramosPedidoValido() throws Exception {
            // Arrange
            String pedidoStatusInicial = "Pedido recebido";

            // pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);
            Long pedidoId = pedido.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", pedido.getId().toString())
                            .param("codigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.PedidoBuilder.class)
                    .build();

            // Assert
            assertAll(
                    () -> assertEquals(pedidoId, resultado.getId().longValue()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(),
                            resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(),
                            modelMapper.map(resultado.getPizzasMedias().get(0).getSabor(),
                                    SaborPostPutRequestDTO.class)),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(),
                            resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco()),
                    () -> assertFalse(resultado.getStatusPagamento()),
                    () -> assertEquals(pedidoStatusInicial, resultado.getStatus()));
        }

        @Test
        @DisplayName("Quando alteramos um pedido inexistente")
        void quandoAlteramosPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", "999999")
                            .param("codigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um pedido passando codigo de acesso invalido")
        void quandoAlteramosPedidoPassandoCodigoAcessoInvalido() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", pedido.getId().toString())
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um pedido passando codigo de acesso valido," +
                " mas que não é o cliente que fez o pedido")
        void quandoAlteramosPedidoPassandoCodigoAcessoValidoClieteInvalido() throws Exception {
            // Arrange
            cliente = clienteRepository.save(Cliente.builder()
                    .nome("Anton Egos")
                    .endereco("Paris Saint German")
                    .codigoAcesso("789101")
                    .build());
            pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS)
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("pedidoId", pedido.getId().toString())
                            .param("codigoAcesso", "789101")
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca por todos seus pedidos salvos")
        void quandoClienteBuscaTodosPedidos() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);
            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS)
                            .param("clienteId", cliente.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Pedido> resultado = objectMapper.readValue(responseJsonString,
                    new TypeReference<List<Pedido>>() {
                    });

            // Assert
            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido seu salvo pelo id primeiro")
        void quandoClienteBuscaPedidoPorId() throws Exception {
            // Arrange
            String pedidoStatusInicial = "Pedido recebido";
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + pedido.getId() + "/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Pedido> listaResultados = objectMapper.readValue(responseJsonString,
                    new TypeReference<List<Pedido>>() {
                    });

            Pedido resultado = listaResultados.get(0);

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(),
                            resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(),
                            modelMapper.map(resultado.getPizzasMedias().get(0).getSabor(),
                                    SaborPostPutRequestDTO.class)),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(),
                            resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco()),
                    () -> assertFalse(resultado.getStatusPagamento()),
                    () -> assertEquals(pedidoStatusInicial, resultado.getStatus()));
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido seu salvo por id inexistente")
        void quandoClienteBuscaPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + "999999" + "/" + cliente.getId())
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca por um pedido feito por outro cliente")
        void quandoClienteBuscaPedidoDeOutroCliente() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);
            Cliente cliente1 = clienteRepository.save(Cliente.builder()
                    .nome("Catarina")
                    .endereco("Casinha")
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + pedido.getId() + "/" + cliente1.getId())
                            .param("clienteCodigoAcesso", cliente1.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca todos os pedidos feitos nele")
        void quandoEstabelecimentoBuscaTodosPedidos() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);
            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + estabelecimento.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Pedido> resultado = objectMapper.readValue(responseJsonString,
                    new TypeReference<List<Pedido>>() {
                    });

            // Assert
            assertEquals(2, resultado.size());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id primeiro")
        void quandoEstabelecimentoBuscaPedidoPorId() throws Exception {
            // Arrange
            String pedidoStatusInicial = "Pedido recebido";
            pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + pedido.getId() + "/" + estabelecimento.getId()
                            + "/" + estabelecimento.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper
                                    .writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<Pedido> listaResultados = objectMapper.readValue(responseJsonString,
                    new TypeReference<List<Pedido>>() {
                    });

            Pedido resultado = listaResultados.get(0);

            // Assert
            assertAll(
                    () -> assertNotNull(resultado.getId()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(),
                            resultado.getEnderecoEntrega()),
                    () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(),
                            modelMapper.map(resultado.getPizzasMedias().get(0).getSabor(),
                                    SaborPostPutRequestDTO.class)),
                    () -> assertEquals(pedido.getClienteId(), resultado.getClienteId()),
                    () -> assertEquals(pedido.getEstabelecimentoId(),
                            resultado.getEstabelecimentoId()),
                    () -> assertEquals(pedido.getPreco(), resultado.getPreco()),
                    () -> assertFalse(resultado.getStatusPagamento()),
                    () -> assertEquals(pedidoStatusInicial, resultado.getStatus()));
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id inexistente")
        void quandoEstabelecimentoBuscaPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + estabelecimento.getCodigoAcesso() + "/"
                            + estabelecimento.getId() + "/" + "999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper
                                    .writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoBuscaPedidoDeOutroEstabelecimento() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pedidoRepository.save(pedido);
            Estabelecimento estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" + pedido.getId() + "/"
                            + estabelecimento1.getId() + "/" + estabelecimento1.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Código de acesso não corresponde com o estabelecimento", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente excluí um pedido feito por ele salvo")
        void quandoClienteExcluiPedidoSalvo() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pedido.setStatus("Pedido entregue");
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente excluí um pedido inexistente")
        void quandoClienteExcluiPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + "999999" + "/" + cliente.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        // Iury
        @Test
        @DisplayName("Quando um cliente excluí todos seus pedidos feitos por ele salvos")
        void quandoClienteExcluiTodosPedidosSalvos() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // DOIS PEDIDOS SALVOS...
            List<Pedido> pedidos = pedidoRepository.findAllByClienteId(cliente.getId());
            assertEquals(2, pedidos.size());

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/")
                            .param("clienteId", cliente.getId().toString())
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            // ESPERADO 0 PEDIDOS JÁ QUE EU O CLIENTE EXCLUI
            pedidos = pedidoRepository.findAllByClienteId(cliente.getId());
            assertEquals(0, pedidos.size());
            assertTrue(responseJsonString.isBlank());

        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido feito nele salvo")
        void quandoEstabelecimentoExcluiPedidoSalvo() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/"
                            + estabelecimento.getId() + "/"
                            + estabelecimento.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido inexistente")
        void quandoEstabelecimentoExcluiPedidoInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + "999999" + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoExcluiPedidoDeOutroEstabelecimento() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            Estabelecimento estabelecimento1 = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("121212")
                    .build());

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/"
                            + estabelecimento1.getId() + "/"
                            + estabelecimento1.getCodigoAcesso())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento1.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Código de acesso não corresponde com o estabelecimento", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí todos os pedidos feitos nele salvos")
        void quandoEstabelecimentoExcluiTodosPedidosSalvos() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();
            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido")
        void quandoClienteCancelaPedido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(delete(URI_PEDIDOS + "/" + pedido.getId() + "/cancelar-pedido")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimento() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + "pedido-cliente-estabelecimento" + "/"
                            + cliente.getId() + "/" + estabelecimento.getId() + "/"
                            + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))

                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultadoFinal = objectMapper.readValue(responseJsonString,
                    PedidoResponseDTO.class);

            List<PedidoResponseDTO> resultado = new ArrayList<PedidoResponseDTO>();
            resultado.add(resultadoFinal);

            // Assert
            assertEquals(1, resultado.size());
            assertEquals(pedido.getId(), resultado.get(0).getId());
            assertEquals(pedido.getClienteId(), resultado.get(0).getClienteId());
            assertEquals(pedido.getEstabelecimentoId(), resultado.get(0).getEstabelecimentoId());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoInexistente() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + "pedido-cliente-estabelecimento" + "/"
                            + cliente.getId() + "/" + "999999" + "/" + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento com pedido inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoComPedidoInexistente() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + "pedido-cliente-estabelecimento" + "/"
                            + cliente.getId() + "/" + estabelecimento.getId() + "/"
                            + "999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento com cliente inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoComClienteInexistente() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/" + "pedido-cliente-estabelecimento" + "/"
                            + "999999" + "/" + estabelecimento.getId() + "/"
                            + pedido.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O cliente consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento com pedidoId")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComPedidoId() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);


            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/pedidos-cliente-estabelecimento/" + cliente.getId()
                            + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                    new TypeReference<>() {
                    });

            // Assert
            assertEquals(2, resultado.size());
            assertEquals(pedido.getId(), resultado.get(0).getId());
            assertEquals(pedido.getClienteId(), resultado.get(0).getClienteId());
            assertEquals(pedido.getEstabelecimentoId(), resultado.get(0).getEstabelecimentoId());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento com status")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComStatus() throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/pedidos-cliente-estabelecimento/" + cliente.getId()
                            + "/" + estabelecimento.getId() + "/"
                            + pedido.getStatus())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                    new TypeReference<>() {
                    });

            // Assert
            assertEquals(2, resultado.size());
            assertEquals(pedido.getId(), resultado.get(0).getId());
            assertEquals(pedido.getClienteId(), resultado.get(0).getClienteId());
            assertEquals(pedido.getEstabelecimentoId(), resultado.get(0).getEstabelecimentoId());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento filtrados por entrega")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComPedidosFiltradosPorEntrega()
                throws Exception {
            // Arrange
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);

            pedidoRepository.save(pedido);
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(get(URI_PEDIDOS + "/pedidos-cliente-estabelecimento/" + cliente.getId()
                            + "/" + estabelecimento.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                    new TypeReference<>() {
                    });

            // Assert
            assertEquals(2, resultado.size());
            assertEquals(pedido.getId(), resultado.get(0).getId());
            assertEquals(pedido.getClienteId(), resultado.get(0).getClienteId());
            assertEquals(pedido.getEstabelecimentoId(), resultado.get(0).getEstabelecimentoId());
            assertEquals(pedido1.getId(), resultado.get(1).getId());
            assertEquals(pedido1.getClienteId(), resultado.get(1).getClienteId());
            assertEquals(pedido1.getEstabelecimentoId(), resultado.get(1).getEstabelecimentoId());

        }

    }

    @Nested
    @DisplayName("Alteração de estado de pedido")
    public class AlteracaoEstadoPedidoTest {
        @BeforeEach
        void setUp() {
            pizzaMediaRepository.save(pizzaMedia);
            pizzaGrandeRepository.save(pizzaGrande);
        }

        @Test
        @DisplayName("Quando o estabelecimento termina o preparo do pedido")
        void quandoEstabelecimentoTerminaPreparo() throws Exception {
            // Arrange
            pedido.setStatus("Pedido em preparo");
            pedidoRepository.save(pedido);
            // Act
            String responseJsonString = driver.perform(
                            put(URI_PEDIDOS + "/pedido-pronto")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("pedidoId", pedido.getId().toString())
                                    .param("estabelecimentoId", estabelecimento.getId().toString())
                                    .param("estabelecimentoCodigoAcesso",
                                            estabelecimento.getCodigoAcesso())
                                    )
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                    PedidoResponseDTO.class);

            // Assert
            assertEquals(resultado.getStatus(), "Pedido pronto");
        }

        @Test
        @DisplayName("Quando o estabelecimento termina o preparo do pedido que não estava em preparo")
        void quandoEstabelecimentoTerminaPreparoSemPreparo() throws Exception {
            // Arrange
            pedido.setStatus("Pedido recebido");
            pedidoRepository.save(pedido);
            // Act
            String responseJsonString = driver.perform(
                            put(URI_PEDIDOS + "/pedido-pronto")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("pedidoId", pedido.getId().toString())
                                    .param("estabelecimentoId", estabelecimento.getId().toString())
                                    .param("estabelecimentoCodigoAcesso",
                                            estabelecimento.getCodigoAcesso())
                    )
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido não está na etapa Pedido em preparo", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando o estabelecimento associa um pedido a um entregador")
        void quandoEstabelecimentoAssociaPedidoEntregador() throws Exception {
            // Arrange
            pedido.setStatus("Pedido pronto");
            entregador.setStatusAprovacao(true);
            entregador.setDisponibilidade(true);
            entregadorRepository.flush();
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver.perform(
                            put(URI_PEDIDOS + "/" + pedido.getId() + "/" + "associar-pedido-entregador/")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("estabelecimentoId", estabelecimento.getId().toString())
                                    .param("estabelecimentoCodigoAcesso",
                                            estabelecimento.getCodigoAcesso())
                                    .content(objectMapper
                                            .writeValueAsString(pedidoPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                    PedidoResponseDTO.class);

            // Assert
            assertEquals(resultado.getStatus(), "Pedido em rota");
            assertEquals(entregador.getId(), resultado.getEntregadorId());
        }

        @Test
        @DisplayName("Quando o estabelecimento associa um pedido a um entregador mas o pedido não esta pronto")
        void quandoEstabelecimentoAssociaPedidoEntregadorSemEstarPronto() throws Exception {
            // Arrange
            pedido.setStatus("Pedido em preparo");
            pedidoRepository.save(pedido);
            entregador.setStatusAprovacao(true);
            Set<Entregador> entregadores = new HashSet<>();
            entregadores.add(entregador);
            estabelecimento.setEntregadoresDisponiveis(entregadores);
            entregador.setDisponibilidade(true);

            // Act
            String responseJsonString = driver.perform(
                            put(URI_PEDIDOS + "/" + pedido.getId() + "/" + "/associar-pedido-entregador/")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("estabelecimentoId", estabelecimento.getId().toString())
                                    .param("estabelecimentoCodigoAcesso",
                                            estabelecimento.getCodigoAcesso())
                                    .content(objectMapper
                                            .writeValueAsString(pedidoPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido não está na etapa pedido pronto", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando o cliente confirma a entrega de um pedido")
        void quandoClienteConfirmaEntregaPedido() throws Exception {
            // Arrange
            pedido1.setStatus("Pedido em rota");
            pedidoRepository.save(pedido1);

            // Act
            String responseJsonString = driver
                    .perform(put(URI_PEDIDOS + "/" + pedido1.getId() + "/" + cliente.getId()
                            + "/cliente-confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper
                                    .writeValueAsString(pedidoPutEntregueRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                    PedidoResponseDTO.class);

            // Assert
            assertEquals(resultado.getStatus(), "Pedido entregue");
        }

        @Test
        @DisplayName("Quando o cliente confirma a entrega de um pedido que não esta em rota")
        void quandoClienteConfirmaPedidoSemEntrega() throws Exception {
            // Arrange
            pedido.setStatus("Pedido pronto");
            pedidoRepository.save(pedido);

            // Act
            String responseJsonString = driver
                    .perform(put(URI_PEDIDOS + "/" + pedido.getId() + "/" + cliente.getId()
                            + "/cliente-confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                            .content(objectMapper
                                    .writeValueAsString(pedidoPutEntregueRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O pedido não está na etapa Pedido em rota", resultado.getMessage());
        }
    }

     // Pedro Vinícius
     @Nested
     @DisplayName("Conjunto de casos de teste da confirmação de pagamento de um pedido")
     public class PedidoConfirmarPagamentoTests {

         Pedido pedido1;

         @BeforeEach
         void setUp() {
             pizzaGrandeRepository.save(pizzaGrande);
             pizzaMediaRepository.save(pizzaMedia);
             pedido1 = pedidoRepository.save(Pedido.builder()
                     .preco(new BigDecimal(35.0))
                     .enderecoEntrega("Casa 237")
                     .clienteId(cliente.getId())
                     .estabelecimentoId(estabelecimento.getId())
                     .entregadorId(entregador.getId())
                     .pizzasMedias(List.of(pizzaMedia))
                     .pizzasGrandes(List.of(pizzaGrande))
                     .statusPagamento(false)
                     .status("Pedido recebido")
                     .build());
         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito")
         void confirmaPagamentoCartaoCredito() throws Exception {
             //Arrange
             String status = "Pedido em preparo";

             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "Cartão de crédito")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isOk())  //Codigo 200
                     .andReturn().getResponse().getContentAsString();
             //Assert
             Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.class);
             assertAll(
                     () -> assertTrue(resultado.getStatusPagamento()),
                     () -> assertEquals(status, resultado.getStatus()),
                     () -> assertEquals(new BigDecimal(35), resultado.getPreco())
             );
         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de débito")
         void confirmaPagamentoCartaoDebito() throws Exception {
             //Arrange
             String status = "Pedido em preparo";
             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "Cartão de débito")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isOk())  //Codigo 200
                     .andReturn().getResponse().getContentAsString();
             //Assert
             Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.class);
             assertAll(
                     () -> assertTrue(resultado.getStatusPagamento()),
                     () -> assertEquals(status, resultado.getStatus()),
                     () -> assertEquals(BigDecimal.valueOf(34.125), resultado.getPreco())
             );

         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por pix")
         void confirmaPagamentoPIX() throws Exception {
             //Arrange
             String status = "Pedido em preparo";
             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "PIX")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isOk())  //Codigo 200
                     .andDo(print())
                     .andReturn().getResponse().getContentAsString();
             //Assert
             Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.class);
             assertAll(
                     () -> assertTrue(resultado.getStatusPagamento()),
                     () -> assertEquals(status, resultado.getStatus()),
                     () -> assertEquals(BigDecimal.valueOf(33.25), resultado.getPreco())
             );
         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito mas o pedido não foi recebido")
         void confirmaPagamentoCartaoCreditoPedidoNaoRecebido() throws Exception {
             //Arrange
             pedido1.setStatus("");

             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "Cartão de crédito")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isBadRequest())  //Codigo 200
                     .andReturn().getResponse().getContentAsString();
             //Assert
             CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

             // Assert
             assertEquals("O pedido não está na etapa pedido recebido", resultado.getMessage());
         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de débito mas o pedido não foi recebido")
         void confirmaPagamentoCartaoDebitoPedidoNaoRecebido() throws Exception {
             //Arrange
             pedido1.setStatus("Pedido pronto");
             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "Cartão de débito")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isBadRequest())  //Codigo 200
                     .andReturn().getResponse().getContentAsString();
             //Assert
             CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

             // Assert
             assertEquals("O pedido não está na etapa pedido recebido", resultado.getMessage());

         }

         @Test
         @DisplayName("Quando confirmamos o pagamento de um pedido por pix mas o pedido não foi recebido")
         void confirmaPagamentoPIXPedidoNaoRecebido() throws Exception {
             //Arrange
             pedido1.setStatus("Pedido em preparo");
             //Act
             String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + cliente.getId() + "/confirmar-pagamento")
                             .contentType(MediaType.APPLICATION_JSON)
                             .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                             .param("pedidoId", pedido1.getId().toString())
                             .param("metodoPagamento", "PIX")
                             .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                     .andExpect(status().isBadRequest())  //Codigo 200
                     .andDo(print())
                     .andReturn().getResponse().getContentAsString();
             //Assert
             CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

             // Assert
             assertEquals("O pedido não está na etapa pedido recebido", resultado.getMessage());
         }

         @Test
         @DisplayName("Quando um pedido fica pronto e tem entregador disponível, ele é atribuído automaticamente e o status é atualizado")
         void quandoPedidoFicaProntoComEntregadorDisponivel() throws Exception {
             // Arrange
             pedido.setStatus("Pedido Pronto");
             pedidoRepository.save(pedido);
             entregador.setStatusAprovacao(true);
             Set<Entregador> entregadores = new HashSet<>();
             entregadores.add(entregador);
             estabelecimento.setEntregadoresDisponiveis(entregadores);

             // Act
             driver.perform(
                             put(URI_PEDIDOS + "/pedido-pronto")
                                     .contentType(MediaType.APPLICATION_JSON)
                                     .param("pedidoId", pedido.getId().toString())
                                     .param("estabelecimentoId", estabelecimento.getId().toString())
                                     .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                     )
                     .andExpect(status().isOk())
                     .andExpect((ResultMatcher) jsonPath("$.status").value("Pedido em rota"))
                     .andExpect((ResultMatcher) jsonPath("$.entregadorId").value(entregador.getId()));
         }
     }

    @Test
    @DisplayName("Quando um entregador fica disponível, um pedido pronto é atribuído automaticamente")
    void quandoEntregadorFicaDisponivelPedidoAtribuidoAutomaticamente() throws Exception {
        // Arrange
        pedido.setStatus("Pedido Pronto");
        pedidoRepository.save(pedido);
        entregador.setStatusAprovacao(true);
        entregador.setDisponibilidade(true);

        // Act
        driver.perform(
                        put(URI_PEDIDOS + "/atribuir-pedido-automaticamente")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("entregadorId", entregador.getId().toString())
                                .param("estabelecimentoId", estabelecimento.getId().toString())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                )
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) jsonPath("$.status").value("Pedido em rota"))
                .andExpect((ResultMatcher) jsonPath("$.entregadorId").value(entregador.getId()));
    }

}