package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorResponseDTO;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Entregadores")
public class EntregadorControllerTests {

    final String URI_ENTREGADORES = "/api/v1/entregadores";

    @Autowired
    MockMvc driver;

    @Autowired
    EntregadorRepository entregadorRepository;

    @Autowired
    ModelMapper modelMapper;

    ObjectMapper objectMapper = new ObjectMapper();

    Entregador entregador;

    Estabelecimento estabelecimento;

    Associacao associacao;

    Sabor sabor;

    PizzaMedia pizzaMedia;

    @Autowired
    PedidoRepository pedidoRepository;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    AssociacaoRepository associacaoRepository;

    @Autowired
    SaborRepository saborRepository;

    @Autowired
    PizzaMediaRepository pizzaMediaRepository;

    Cliente cliente;

    Pedido pedido;

    EntregadorPostPutRequestDTO entregadorPostPutRequestDTO;

    EntregadorGetRequestDTO entregadorDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Lana Del Rey")
                .placaVeiculo("ABC-1234")
                .corVeiculo("Azul")
                .tipoVeiculo("moto")
                .codigoAcesso("123456")
                .build()
        );
        entregadorPostPutRequestDTO = EntregadorPostPutRequestDTO.builder()
                .nome(entregador.getNome())
                .codigoAcesso(entregador.getCodigoAcesso())
                .placaVeiculo(entregador.getPlacaVeiculo())
                .corVeiculo(entregador.getCorVeiculo())
                .tipoVeiculo(entregador.getTipoVeiculo())
                .build();

        entregadorDTO = modelMapper.map(entregador, EntregadorGetRequestDTO.class);
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de fluxos básicos API Rest")
    class EntregadorVerificacaoFluxosBasicosApiRest {

        @Test
        @DisplayName("Quando buscamos por todos entregadores salvos")
        void quandoBuscamosTodosEntregadores() throws Exception {
            // Arrange
            // Vamos ter 3 entregadores no banco
            Entregador entregador1 = Entregador.builder()
                    .nome("Jose")
                    .placaVeiculo("GHF-1212")
                    .corVeiculo("Prata")
                    .tipoVeiculo("carro")
                    .codigoAcesso("654321")
                    .build();
            Entregador entregador2 = Entregador.builder()
                    .nome("Halloran")
                    .placaVeiculo("MRD-0217")
                    .corVeiculo("Preto")
                    .tipoVeiculo("carro")
                    .codigoAcesso("217217")
                    .build();
            entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertEquals(3, resultado.size());
        }

        @Test
        @DisplayName("Quando buscamos por um entregador salvo pelo id")
        void quandoBuscamosEntregadorPorId() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorDTO.getId(), resultado.getId()),
                    () -> assertEquals(entregadorDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorDTO.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregadorDTO.getCorVeiculo(), resultado.getCorVeiculo()),
                    () -> assertEquals(entregadorDTO.getTipoVeiculo(), resultado.getTipoVeiculo())
            );
        }

        @Test
        @DisplayName("Quando buscamos um entregador inexistente")
        void quandoBuscamosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Entregador não cadastrado", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando criamos um entregador com dados válidos")
        void quandoCriamosEntregadorValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorPostPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getCorVeiculo(), resultado.getCorVeiculo()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getTipoVeiculo(), resultado.getTipoVeiculo())
            );
        }

        @Test
        @DisplayName("Quando criamos um entregador com codigo de acesso contendo letras")
        void quandoCriamosEntregadorComCodigoAcessoComLetras() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()
            entregadorPostPutRequestDTO.setCodigoAcesso("21A222");

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertAll(
                    () -> assertEquals("O código de acesso deve conter exatamente 6 dígitos numericos", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando criamos um entregador com codigo de acesso menor que 6 digitos")
        void quandoCriamosEntregadorComCodigoAcessoMenorQue6Digitos() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()
            entregadorPostPutRequestDTO.setCodigoAcesso("12222");

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertAll(
                    () -> assertEquals("O código de acesso deve conter exatamente 6 dígitos numericos", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando criamos um entregador com codigo de acesso maior que 6 digitos")
        void quandoCriamosEntregadorComCodigoAcessoMaiorQue6Digitos() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()
            entregadorPostPutRequestDTO.setCodigoAcesso("1222211");

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            assertAll(
                    () -> assertEquals("O código de acesso deve conter exatamente 6 dígitos numericos", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador com dados válidos")
        void quandoAlteramosEntregadorValido() throws JsonProcessingException, UnsupportedEncodingException, Exception {
            // Arrange
            Long entregadorId = entregador.getId();

            // Act
            String responseJsonString = driver.perform(MockMvcRequestBuilders.put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorId, resultado.getId().longValue()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getPlacaVeiculo(), resultado.getPlacaVeiculo()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getCorVeiculo(), resultado.getCorVeiculo()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getTipoVeiculo(), resultado.getTipoVeiculo())
            );
        }

        @Test
        @DisplayName("Quando alteramos um entregador inexistente")
        void quandoAlteramosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(MockMvcRequestBuilders.put(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Entregador não cadastrado", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um entregador passando um código de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando excluímos um entregador salvo")
        void quandoExcluimosEntregadorSalvo() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando excluímos um entregador inexistente")
        void quandoExcluimosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Entregador não cadastrado", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando excluímos um entregador passando um código de acesso inválido")
        void quandoExcluimosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999"))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }
    }


    @Nested
    @DisplayName("Conjunto de casos de verificação de nome")
    class EntregadorVerificacaoNome {

        @Test
        @DisplayName("Quando alteramos o nome do entregador com dados válidos")
        void quandoAlteramosNomeDoEntregadorValido() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setNome("Lana Del Rey Alterada");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("Lana Del Rey Alterada", resultado.getNome());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com nome vazio")
        void quandoAlteramosEntregadorComNomeVazio() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setNome("");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                    () -> assertEquals("Nome do entregador obrigatorio", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de placa")
    class EntregadorVerificacaoPlaca {

        @Test
        @DisplayName("Quando alteramos o entregador com placa válida")
        void quandoAlteramosEntregadorComPlacaValida() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setPlacaVeiculo("DEF-3456");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("DEF-3456", resultado.getPlacaVeiculo());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com placa vazia")
        void quandoAlteramosEntregadorComPlacaVazia() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setPlacaVeiculo("");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                    () -> assertEquals("Placa do veiculo obrigatoria", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de tipo")
    class EntregadorVerificacaoTipo {

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo válido")
        void quandoAlteramosEntregadorComTipoVeiculoValido() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setTipoVeiculo("carro");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("carro", resultado.getTipoVeiculo());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo nulo")
        void quandoAlteramosEntregadorComTipoVeiculoVazio() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setTipoVeiculo(null);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                    () -> assertEquals("Tipo do veiculo obrigatorio", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo inválido")
        void quandoAlteramosEntregadorComTipoVeiculoInvalido() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setTipoVeiculo("bicicleta");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Tipo do veiculo deve ser moto ou carro", resultado.getMessage())
            );
        }

        @Test
        @DisplayName("Quando alteramos o tipo passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de cor do veiculo")
    class EntregadorVerificacaoCorVeiculo {

        @Test
        @DisplayName("Quando alteramos o entregador com cor do veiculo válida")
        void quandoAlteramosEntregadorComCorVeiculoValida() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setCorVeiculo("preto");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("preto", resultado.getCorVeiculo());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com cor do veiculo vazia")
        void quandoAlteramosEntregadorComCorVeiculoVazia() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setCorVeiculo("");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                    () -> assertEquals("Cor do veiculo obrigatoria", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos a cor do veiculo passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de alteração de disponibilidade do entregador")
    class EntregadorDefinirDisponibilidade {

        @Test
        @DisplayName("Quando alteramos a disponibilidade do entregador para disponível")
        void quandoAlteramosDisponibilidadeParaDisponivel() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade do entregador para indisponível")
        void quandoAlteramosDisponibilidadeParaIndisponivel() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "false")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade de um entregador inexistente")
        void quandoAlteramosDisponibilidadeDeEntregadorInexistente() throws JsonProcessingException, UnsupportedEncodingException, Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + 999999 + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Entregador não cadastrado", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade de um entregador passando codigo de acesso inválido")
        void quandoAlteramosDisponibilidadeDeEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(patch(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso não correspode com o deste entregador!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um entregador fica disponível, um pedido pronto é atribuído automaticamente")
        void quandoEntregadorFicaDisponivelPedidoAtribuidoAutomaticamente() throws Exception {
            // Arrange
            estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                    .codigoAcesso("654321")
                    .build());
            sabor = saborRepository.save(Sabor.builder()
                    .nome("Sabor Um")
                    .tipo("salgado")
                    .precoM(new BigDecimal(10.0))
                    .precoG(new BigDecimal(20.0))
                    .disponivel(true)
                    .build());
            cliente = clienteRepository.save(Cliente.builder()
                    .nome("Anton Ego")
                    .endereco("Paris")
                    .codigoAcesso("123456")
                    .build());
            associacao = associacaoRepository.save(
                    Associacao.builder()
                            .estabelecimentoId(estabelecimento.getId())
                            .entregadorId(entregador.getId())
                            .status(true)
                            .build()
            );
            pizzaMedia = PizzaMedia.builder()
                    .sabor(sabor)
                    .build();
            pedido = pedidoRepository.save(Pedido.builder()
                    .preco(new BigDecimal(10.0))
                    .enderecoEntrega("Casa 237")
                    .clienteId(cliente.getId())
                    .estabelecimentoId(estabelecimento.getId())
                    .entregadorId(null)
                    .pizzasMedias(List.of(pizzaMedia))
                    .pizzasGrandes(List.of())
                    .statusPagamento(true)
                    .status("Pedido pronto")
                    .aguardandoAssociarEntregador(true)
                    .build());

            // Act
            driver.perform(
                            patch(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("entregadorId", entregador.getId().toString())
                                    .param("codigoAcesso", entregador.getCodigoAcesso())
                                    .param("disponibilidade", "true")
                    )
                    .andExpect(status().isOk())
                    .andDo(print());

            Pedido novoPedido = pedidoRepository.findById(pedido.getId()).get();

            assertAll(
                    () -> assertEquals(entregador.getId(), novoPedido.getEntregadorId()),
                    () -> assertFalse(novoPedido.isAguardandoAssociarEntregador()),
                    () -> assertEquals("Pedido em rota", novoPedido.getStatus())
            );

            clienteRepository.deleteAll();
            estabelecimentoRepository.deleteAll();
            pedidoRepository.deleteAll();
            pizzaMediaRepository.deleteAll();
            saborRepository.deleteAll();
            entregadorRepository.deleteAll();
        }
    }
}