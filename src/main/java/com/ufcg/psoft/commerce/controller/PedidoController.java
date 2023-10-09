package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
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

        @Autowired
        PedidoEstabelecimentoDeleteService pedidoEstabelecimentoDeleteService;

        @Autowired
        PedidoClienteBuscarService pedidoClienteBuscarService;

        @Autowired
        ClienteConfirmaEntrega clienteConfirmaEntrega;

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
                                                pedidoPostPutRequestDTO));
        }

        @PutMapping()
        public ResponseEntity<?> alterarPedido(
                        @RequestParam("pedidoId") Long pedidoId,
                        @RequestParam("codigoAcesso") String codigoAcesso,
                        @Valid @RequestBody PedidoPostPutRequestDTO pedidoPostPutRequestDTO) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(pedidoAlterarService.alterarPedido(
                                                codigoAcesso,
                                                pedidoId,
                                                pedidoPostPutRequestDTO));
        }

        @GetMapping
        public ResponseEntity<?> listaPedidosCliente(
                        @RequestParam("clienteId") Long clienteId) {
                return ResponseEntity.status(HttpStatus.OK).body(
                                pedidoGetAllByClienteService.listarPedidosCliente(clienteId));
        }

        @GetMapping("/{pedidoId}/{clienteId}")
        public ResponseEntity<?> buscarPedido(
                        @PathVariable Long pedidoId,
                        @PathVariable Long clienteId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(
                                pedidoGetByClienteService.pegarPedido(pedidoId, clienteId, clienteCodigoAcesso)));
        }

        @GetMapping("/{estabelecimentoId}")
        public ResponseEntity<?> listaPedidosEstabelecimento(
                        @PathVariable("estabelecimentoId") Long estabelecimentoId) {
                return ResponseEntity.status(HttpStatus.OK).body(
                                pedidoGetAllByEstabelecimentoService.listarPedidosEstabelecimento(estabelecimentoId));
        }

        @GetMapping("/{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
        public ResponseEntity<?> buscarPedidoEstabelecimento(
                        @PathVariable Long pedidoId,
                        @PathVariable Long estabelecimentoId,
                        @PathVariable("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(List.of(
                                pedidoGetByEstabelecimentoService.pegarPedido(pedidoId, estabelecimentoId,
                                                estabelecimentoCodigoAcesso)));
        }

        @DeleteMapping("/{pedidoId}/{clienteId}")
        public ResponseEntity<?> deletePedidoIdCliente(
                        @PathVariable Long pedidoId,
                        @PathVariable Long clienteId,
                        @RequestParam("codigoAcesso") String codigoAcesso) {
                pedidoClienteDeleteService.clienteDeletaPedido(
                                pedidoId,
                                clienteId,
                                codigoAcesso);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @DeleteMapping("/")
        public ResponseEntity<?> deleteTodosPedidosFeitosCliente(
                        @RequestParam("clienteId") String clienteId) {
                pedidoClienteDeleteService.clienteDeleteTodosPedidosFeitos(Long.parseLong(clienteId));
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @DeleteMapping("/{pedidoId}/{estabelecimentoId}/{estabelecimentoCodigoAcesso}")
        public ResponseEntity<?> deletePedidoIdEstabelecimento(
                        @PathVariable("pedidoId") Long pedidoId,
                        @PathVariable("estabelecimentoId") Long estabelecimentoId,
                        @PathVariable("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso,
                        @RequestParam("codigoAcesso") String codigoAcesso) {
                pedidoEstabelecimentoDeleteService.deletePedido(pedidoId, estabelecimentoId,
                                estabelecimentoCodigoAcesso, codigoAcesso);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @DeleteMapping("/{estabelecimentoId}")
        public ResponseEntity<?> deleteTodosPedidosEstabelecimento(
                        @PathVariable("estabelecimentoId") Long estabelecimentoId,
                        @RequestParam("codigoAcesso") String codigoAcesso) {
                pedidoEstabelecimentoDeleteService.deleteTodosPedidos(estabelecimentoId,
                                codigoAcesso);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

        }

        @DeleteMapping("/{pedidoId}/cancelar-pedido")
        public ResponseEntity<?> cancelarPedidoIdCliente(
                        @PathVariable("pedidoId") Long pedidoId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                pedidoClienteDeleteService.cancelarPedido(pedidoId, clienteCodigoAcesso);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @GetMapping("/pedido-cliente-estabelecimento/{clienteId}/{estabelecimentoId}/{pedidoId}")
        public ResponseEntity<?> buscarPedidoFeitoEmEstabelecimentoPorClienteId(
                        @PathVariable Long clienteId,
                        @PathVariable Long estabelecimentoId,
                        @PathVariable Long pedidoId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(pedidoClienteBuscarService.buscarPedidoById(clienteId,
                                estabelecimentoId, pedidoId, clienteCodigoAcesso));
        }

        @GetMapping("/pedidos-cliente-estabelecimento/{clienteId}/{estabelecimentoId}")
        public ResponseEntity<?> buscarPedidosFeitoEmEstabelecimentoPorClienteId(
                        @PathVariable Long clienteId,
                        @PathVariable Long estabelecimentoId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(pedidoClienteBuscarService.buscarPedidos(clienteId,
                                estabelecimentoId, clienteCodigoAcesso));
        }

        @GetMapping("/pedidos-cliente-estabelecimento/{clienteId}/{estabelecimentoId}/{status}")
        public ResponseEntity<?> buscarPedidosFeitoEmEstabelecimentoPorClienteIdPorStatus(
                        @PathVariable Long clienteId,
                        @PathVariable Long estabelecimentoId,
                        @PathVariable String status,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(pedidoClienteBuscarService
                                .buscarPedidoPorStatus(clienteId, estabelecimentoId, status, clienteCodigoAcesso));
        }

        @GetMapping("/pedidos-cliente-estabelecimento/{clienteId}/{estabelecimentoId}/")
        public ResponseEntity<?> buscarPedidosFeitoEmEstabelecimentoPorClienteIdPorEntrega(
                        @PathVariable Long clienteId,
                        @PathVariable Long estabelecimentoId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK).body(pedidoClienteBuscarService
                                .buscarPedidosPorEntrega(clienteId, estabelecimentoId, clienteCodigoAcesso));
        }

        @PutMapping("/{pedidoId}/associar-pedido-entregador/")
        public ResponseEntity<?> estabelecimentoAssociaEntregaParaEntregador(
                        @PathVariable() Long pedidoId,
                        @RequestParam("estabelecimentoId") Long estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso,
                        @Valid @RequestBody PedidoPutRequestDTO pedidoPutRequestDTO) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(pedidoAlterarService.associarEntregador(
                                                pedidoId,
                                                estabelecimentoId,
                                                estabelecimentoCodigoAcesso,
                                                pedidoPutRequestDTO));
        }

        @PutMapping("/{pedidoId}/{clienteId}/cliente-confirmar-entrega")
        public ResponseEntity<?> clienteConfirmaEntrega(
                        @PathVariable() Long pedidoId,
                        @PathVariable() Long clienteId,
                        @RequestParam("clienteCodigoAcesso") String clienteCodigoAcesso,
                        @Valid @RequestBody PedidoPutRequestDTO pedidoPutRequestDTO) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(clienteConfirmaEntrega.confirmaEntrega(
                                                pedidoId,
                                                clienteId,
                                                clienteCodigoAcesso,
                                                pedidoPutRequestDTO));
        }
}