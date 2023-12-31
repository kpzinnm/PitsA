package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoCardapioDTO;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoResponseDTO;
import com.ufcg.psoft.commerce.dto.sabores.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.repository.EstabelecimentoRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de estabelecimentos")
public class EstabelecimentoControllerTests {
        final String URI_ESTABELECIMENTOS = "/api/v1/estabelecimentos";
        EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO;
        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO;

        EstabelecimentoCardapioDTO estabelecimentoCardapioDTO;

        @Autowired
        MockMvc driver;

        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;

        ObjectMapper objectMapper = new ObjectMapper();

        Estabelecimento estabelecimento;

        @BeforeEach
        void setup() {
                objectMapper.registerModule(new JavaTimeModule());
                estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                                .codigoAcesso("123456")
                                .build());

                estabelecimentoPutRequestDTO = EstabelecimentoPutRequestDTO.builder()
                                .codigoAcesso("123456")
                                .build();
                estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                                .codigoAcesso("654321")
                                .build();
        }

        @AfterEach
        void tearDown() {
                estabelecimentoRepository.deleteAll();
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
        class EstabelecimentoVerificacaoFluxosBasicosApiRest {
                EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO;
                EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO;

                @BeforeEach
                void setup() {
                        estabelecimentoPutRequestDTO = EstabelecimentoPutRequestDTO.builder()
                                        .codigoAcesso("123456")
                                        .build();
                        estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                                        .codigoAcesso("654321")
                                        .build();
                }

                @Test
                @DisplayName("Quando criamos um novo estabelecimento com dados válidos")
                void quandoCriarEstabelecimentoValido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("codigoAcesso", estabelecimentoPostPutRequestDTO.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isCreated()) // Codigo 201
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        EstabelecimentoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                                        EstabelecimentoResponseDTO.EstabelecimentoResponseDTOBuilder.class).build();

                        // Assert
                        assertAll(
                                        () -> assertNotNull(resultado.getId()),
                                        () -> assertEquals(estabelecimentoPostPutRequestDTO.getCodigoAcesso(),
                                                        resultado.getCodigoAcesso()));
                }

                @Test
                @DisplayName("Quando excluímos um estabelecimento salvo")
                void quandoExcluimosEstabelecimentoValido() throws Exception {
                        // Arrange
                        // nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver
                                        .perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                                        .andExpect(status().isNoContent()) // Codigo 204
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        // Assert
                        assertTrue(responseJsonString.isBlank());
                }

                @Test
                @DisplayName("Quando atualizamos um estabelecimento salvo")
                void quandoAtualizamosEstabelecimentoValido() throws Exception {
                        // Arrange
                        estabelecimentoPutRequestDTO.setCodigoAcesso("131289");

                        // Act
                        String responseJsonString = driver
                                        .perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                                                        .content(objectMapper.writeValueAsString(
                                                                        estabelecimentoPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        EstabelecimentoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                                        EstabelecimentoResponseDTO.EstabelecimentoResponseDTOBuilder.class).build();

                        // Assert
                        assertAll(
                                        () -> assertEquals(resultado.getId().longValue(),
                                                        estabelecimento.getId().longValue()),
                                        () -> assertEquals("131289", resultado.getCodigoAcesso()));
                }

                @Test
                @DisplayName("Quando alteramos um estabelecimento com codigo de acesso inválido")
                void quandoAlterarEstabelecimentoInvalido() throws Exception {
                        // Arrange
                        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO
                                        .builder()
                                        .codigoAcesso("13")
                                        .build();

                        // Act
                        String responseJsonString = driver
                                        .perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                                                        .content(objectMapper.writeValueAsString(
                                                                        estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Codigo de acesso deve ter exatamente 6 digitos numericos",
                                                        resultado.getErrors().get(0)));
                }

                @Test
                @DisplayName("Quando deletamos um estabelecimento com codigo de acesso inválido")
                void quandoDeltamarEstabelecimentoInvalido() throws Exception {

                        String responseJsonString = driver
                                        .perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("codigoAcesso", "741963"))
                                        .andExpect(status().isBadRequest())
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        assertEquals("Código de acesso não corresponde com o estabelecimento", resultado.getMessage());

                }

                @Test
                @DisplayName("Quando criamos um novo estabelecimento com dados inválidos")
                void quandoCriarEstabelecimentoInvalido() throws Exception {
                        // Arrange
                        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO
                                        .builder()
                                        .codigoAcesso("13")
                                        .build();

                        // Act
                        String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("codigoAcesso", estabelecimentoPostPutRequestDTO.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("Codigo de acesso deve ter exatamente 6 digitos numericos",
                                                        resultado.getErrors().get(0)));
                }

                @Test
                @DisplayName("Quando deletamos um estabelecimento que não existe")
                void quandoDeltamarEstabelecimentoInexistente() throws Exception {

                        String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + 4L)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                                        .andExpect(status().isBadRequest())
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        assertEquals("Estabelecimento não existe", resultado.getMessage());

                }

                @Test
                @DisplayName("Quando alteramos um estabelecimento com codigo de acesso inválido")
                void quandoAlterarEstabelecimentoInexistente() throws Exception {
                        // Arrange
                        EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO
                                        .builder()
                                        .codigoAcesso("134697")
                                        .build();

                        // Act
                        String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + 4L)
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("Estabelecimento não existe", resultado.getMessage());

                }

                // iury

                @Test
                @DisplayName("Quando buscamos o cardapio de um estabelecimento")
                void quandoBuscarCardapioEstabelecimento() throws Exception {
                        // Arrange
                        Sabor sabor1 = Sabor.builder()
                                        .nome("Calabresa")
                                        .precoM(new BigDecimal("25.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor2 = Sabor.builder()
                                        .nome("Mussarela")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("29.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();
                        Sabor sabor3 = Sabor.builder()
                                        .nome("Chocolate")
                                        .precoM(new BigDecimal("15.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor4 = Sabor.builder()
                                        .nome("Morango")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("40.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();

                        Estabelecimento estabelecimento1 = Estabelecimento.builder()
                                        .codigoAcesso("122456")
                                        .sabores(Set.of(sabor1, sabor2, sabor3, sabor4))
                                        .build();
                        
                                        estabelecimentoRepository.save(estabelecimento1);

                        // Act
                        String responseJsonString = driver
                                        .perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento1.getId() + "/sabores")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        List<SaborResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                                        new TypeReference<List<SaborResponseDTO>>() {
                                        });
                        // Assert
                        assertAll(
                                        () -> assertEquals(4, resultado.size()));
                }

                @Test
                @DisplayName("Quando buscamos o cardapio de um estabelecimento que não existe")
                void quandoBuscarCardapioEstabelecimentoInexistente() throws Exception {
                        // Arrange
                        // Nenhuma necessidade além do setup()

                        // Act
                        String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 9999 + "/sabores")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 404
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Estabelecimento não existe", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (salgado)")
                void quandoBuscarCardapioEstabelecimentoPorTipo() throws Exception {
                        // Arrange
                        Sabor sabor1 = Sabor.builder()
                                        .nome("Calabresa")
                                        .precoM(new BigDecimal("25.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor2 = Sabor.builder()
                                        .nome("Mussarela")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("29.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();
                        Sabor sabor3 = Sabor.builder()
                                        .nome("Chocolate")
                                        .precoM(new BigDecimal("15.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor4 = Sabor.builder()
                                        .nome("Morango")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("40.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();
                        Estabelecimento estabelecimento1 = Estabelecimento.builder()
                                        .codigoAcesso("555555")
                                        .sabores(Set.of(sabor1, sabor2, sabor3, sabor4))
                                        .build();
                        estabelecimentoRepository.save(estabelecimento1);

                        // Act
                        String responseJsonString = driver.perform(
                                        get(URI_ESTABELECIMENTOS + "/" + estabelecimento1.getId() + "/cardapio" + "/tipo")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("tipo", "salgado")
                                                        .content(objectMapper
                                                                        .writeValueAsString(
                                                                                        estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        List<SaborResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                                        new TypeReference<>() {
                                        });

                        // Assert
                        assertAll(
                                        () -> assertEquals(2, resultado.size()));
                }

                @Test
                @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (doce)")
                void quandoBuscarCardapioEstabelecimentoPorTipoDoce() throws Exception {
                        // Arrange
                        Sabor sabor1 = Sabor.builder()
                                        .nome("Calabresa")
                                        .precoM(new BigDecimal("25.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor2 = Sabor.builder()
                                        .nome("Mussarela")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("29.0"))
                                        .tipo("salgado")
                                        .disponivel(true)
                                        .build();
                        Sabor sabor3 = Sabor.builder()
                                        .nome("Chocolate")
                                        .precoM(new BigDecimal("15.0"))
                                        .precoG(new BigDecimal("30.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();

                        Sabor sabor4 = Sabor.builder()
                                        .nome("Morango")
                                        .precoM(new BigDecimal("20.0"))
                                        .precoG(new BigDecimal("40.0"))
                                        .tipo("doce")
                                        .disponivel(true)
                                        .build();
                        Estabelecimento estabelecimento1 = Estabelecimento.builder()
                                        .codigoAcesso("444444")
                                        .sabores(Set.of(sabor1, sabor2, sabor3, sabor4))
                                        .build();
                        estabelecimentoRepository.save(estabelecimento1);

                        // Act
                        String responseJsonString = driver.perform(
                                        get(URI_ESTABELECIMENTOS + "/" + estabelecimento1.getId() + "/cardapio"
                                                        + "/tipo")
                                                        .contentType(MediaType.APPLICATION_JSON)
                                                        .param("tipo", "doce")
                                                        .content(objectMapper
                                                                        .writeValueAsString(
                                                                                        estabelecimentoPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        List<SaborResponseDTO> resultado = objectMapper.readValue(responseJsonString,
                                        new TypeReference<>() {
                                        });

                        // Assert
                        assertAll(
                                        () -> assertEquals(2, resultado.size()));
                }
        }
}
