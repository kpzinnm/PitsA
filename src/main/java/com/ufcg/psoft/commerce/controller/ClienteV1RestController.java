package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.cliente.ClientePostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Cliente;
import com.ufcg.psoft.commerce.services.cliente.*;

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
    ){
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteGetByIdService.getCliente(id));
    }

    @GetMapping("")
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

    @PutMapping("/{id}/demonstrarInteresse")
    public ResponseEntity<?> demonstrarInteresseEmSabor(
            @PathVariable("id") Long clienteId,
            @Valid @RequestParam("saborId") Long saborId,
            @RequestParam("codigoAcesso") String codigoAcesso
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(clienteDemonstrarInteresseService.demonstraInteresse(saborId, clienteId, codigoAcesso));
    }

}
