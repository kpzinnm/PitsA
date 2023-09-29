package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.services.cliente.ClienteAtualizarService;
import com.ufcg.psoft.commerce.services.cliente.ClienteCriarService;
import com.ufcg.psoft.commerce.services.cliente.ClienteGetByIdService;
import com.ufcg.psoft.commerce.services.cliente.ClienteLerTodosService;
import com.ufcg.psoft.commerce.services.cliente.ClienteRemoverService;

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
        produces = MediaType.APPLICATION_JSON_VALUE
)
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

    @PostMapping()
    public ResponseEntity<?> criarCliente(
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
            ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteCriarService.criarCliente
                        (modelMapper.map(clientePostPutRequestDTO, Cliente.class)));

    }

    @GetMapping("{id}")
    public ResponseEntity<?> lerCliente(
            @PathVariable("id") Long id
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteGetByIdService.getCliente(id));
    }

    @GetMapping()
    public ResponseEntity<?> lerTodosClientes(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteLerTodosService.getAllClientes());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> removeCliente(
            @PathVariable("id") Long id,
            @RequestParam("codigoAcesso") String codigoAcesso
    ){
        clienteRemoverService.removeCliente(id, codigoAcesso);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarCliente(
            @PathVariable("id") Long id,
            @RequestParam("codigoAcesso") String codigoAcesso,
            @RequestBody @Valid ClientePostPutRequestDTO clientePostPutRequestDTO
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteAtualizarService.atualizaCliente(clientePostPutRequestDTO, id, codigoAcesso));
    }

}