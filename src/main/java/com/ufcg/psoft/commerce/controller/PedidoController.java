package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.services.pedido.PedidoCriarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

        @Autowired
        PedidoCriarService pedidoCriarService;

        @PostMapping()
        public ResponseEntity<?> criarPedido(@RequestParam("clienteId") Long clienteId,
                                             @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso,
                                             @RequestParam("estabelecimentoId") Long estabelecimentoId,
                                             @Valid @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {

                try {
                        return ResponseEntity.status(HttpStatus.CREATED)
                                .body(pedidoCriarService.criarPedido(
                                        clienteId,
                                        clienteCodigoAcesso,
                                        estabelecimentoId,
                                        pedidoPostPutRequestDTO
                                ));
                } catch (CommerceException e) {
                        return ResponseEntity
                                .status(HttpStatus.BAD_REQUEST)
                                .body(e);
                }


        }

}