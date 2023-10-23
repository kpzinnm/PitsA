package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.services.cliente.*;

import com.ufcg.psoft.commerce.services.pedido.PedidoClienteFilterStatusGetService;
import com.ufcg.psoft.commerce.services.pedido.PedidoGetByClienteService;
import com.ufcg.psoft.commerce.services.pedido.PedidoStatusOrdenadoGetByClienteService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/api/v1/clientes",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class ClienteV1RestController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    ClienteCriarService clienteCriarService;

    @Autowired
    ClienteGetByIdService clienteGetByIdService;

    @Autowired
    ClienteLerTodosService clienteLerTodosService;

    @Autowired
    ClienteRemoverService clienteRemoverService;

    @Autowired
    ClienteAtualizarService clienteAtualizarService;

    @Autowired
    ClienteDemonstrarInteresseService clienteDemonstrarInteresseService;

    @Autowired
    PedidoGetByClienteService pedidoGetByClienteService;

    @Autowired
    PedidoStatusOrdenadoGetByClienteService pedidoStatusOrdenadoGetByClienteService;

    @Autowired
    PedidoClienteFilterStatusGetService pedidoClienteFilterStatusGetService;

    @PostMapping()
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteCriarService.criarCliente
                        (modelMapper.map(clientePostPutRequestDTO, Cliente.class)));

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> lerCliente(
            @PathVariable("id") Long id
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteGetByIdService.getCliente(id));
    }

    @GetMapping("")
    public ResponseEntity<?> lerTodosClientes() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteLerTodosService.getAllClientes());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeCliente(
            @PathVariable("id") Long id,
            @RequestParam("codigoAcesso") String codigoAcesso
    ) {
        clienteRemoverService.removeCliente(id, codigoAcesso);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable("id") Long id,
            @RequestParam("codigoAcesso") String codigoAcesso,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteAtualizarService.atualizaCliente(clientePostPutRequestDTO, id, codigoAcesso));
    }

    @PutMapping("/{clienteId}/demonstrarInteresse")
    public ResponseEntity<?> demonstrarInteresseEmSabor(
            @PathVariable("clienteId") Long clienteId,
            @Valid @RequestParam("codigoAcesso") String codigoAcesso,
            @Valid @RequestParam("saborId") Long saborId

    ) {
        return ResponseEntity.status(HttpStatus.OK).body(clienteDemonstrarInteresseService.demonstraInteresse(saborId, clienteId, codigoAcesso));
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<?> lerPedido(
            @PathVariable("clienteId") Long clienteId,
            @Valid @RequestParam("codigoAcesso") String codigoAcesso,
            @Valid @RequestParam("pedidoId") Long pedidoId
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoGetByClienteService.pegarPedido(pedidoId, clienteId, codigoAcesso));
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<?> listarPedidosCliente(@PathVariable Long clienteId) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoStatusOrdenadoGetByClienteService.listarPedidosCliente(clienteId));
    }

    @GetMapping("/{clienteId}/pedidos")
    public ResponseEntity<?> listarPedidosClientePorStatus(
            @PathVariable Long clienteId,
            @RequestParam("pedidoStatus") String pedidoStatus
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(pedidoClienteFilterStatusGetService.listarPedidosClientePorStatus(clienteId,pedidoStatus ));
    }
}
