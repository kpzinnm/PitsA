package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.services.pedido.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/pedidos", produces = MediaType.APPLICATION_JSON_VALUE)
public class PedidoController {

        @Autowired
        PedidoCriarService pedidoCriarService;

        @Autowired
        PedidoAlterarService pedidoAlterarService;

        @Autowired
        PedidoGetAllByClienteService pedidoGetAllByClienteService;

        @Autowired
        PedidoGetByClienteService pedidoGetByClienteService;

        @Autowired
        PedidoGetByEstabelecimentoService pedidoGetByEstabelecimentoService;

        @Autowired
        PedidoGetAllByEstabelecimentoService pedidoGetAllByEstabelecimentoService;

        @Autowired
        PedidoClienteDeleteService pedidoClienteDeleteService;

        @PostMapping
        public ResponseEntity<?> criarPedido(@RequestParam("clienteId") Long clienteId,
                                             @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso,
                                             @RequestParam("estabelecimentoId") Long estabelecimentoId,
                                             @Valid @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(pedidoCriarService.criarPedido(
                                clienteId,
                                clienteCodigoAcesso,
                                estabelecimentoId,
                                pedidoPostPutRequestDTO
                        ));
        }

        @PutMapping()
        public ResponseEntity<?> alterarPedido(
                @RequestParam("pedidoId") Long pedidoId,
                @RequestParam("codigoAcesso") String codigoAcesso,
                @Valid @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO
        ) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(pedidoAlterarService.alterarPedido(
                                codigoAcesso,
                                pedidoId,
                                pedidoPostPutRequestDTO
                        ));
        }

        @GetMapping
        public ResponseEntity<?> listaPedidosCliente(
                @RequestParam("clienteId") Long clienteId
        ) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        pedidoGetAllByClienteService.listarPedidosCliente(clienteId)
                );
        }

        @GetMapping("/{pedidoId}/{clienteId}")
        public ResponseEntity<?> buscarPedido(
                @PathVariable Long pedidoId,
                @PathVariable Long clienteId,
                @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso
        ) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(
                        pedidoGetByClienteService.pegarPedido(pedidoId, clienteId, clienteCodigoAcesso))
                );
        }

        @GetMapping("/{estabelecimentoId}")
        public ResponseEntity<?> listaPedidosEstabelecimento(
                @PathVariable("estabelecimentoId") Long estabelecimentoId
        ) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        pedidoGetAllByEstabelecimentoService.listarPedidosEstabelecimento(estabelecimentoId)
                );
        }

        @GetMapping("/{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
        public ResponseEntity<?> buscarPedidoEstabelecimento(
                @PathVariable Long pedidoId,
                @PathVariable Long estabelecimentoId,
                @PathVariable("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso
        ) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(
                        pedidoGetByEstabelecimentoService.pegarPedido(pedidoId, estabelecimentoId, estabelecimentoCodigoAcesso))
                );
        }

        @DeleteMapping("/{pedidoId}/{clienteId}")
        public ResponseEntity<?> cancelarPedido(
                @PathVariable Long pedidoId,
                @PathVariable Long clienteId,
                @RequestParam("codigoAcesso") String codigoAcesso
        ) {
                pedidoClienteDeleteService.clienteDeletaPedido(
                        pedidoId,
                        clienteId,
                        codigoAcesso
                );
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
}