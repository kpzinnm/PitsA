package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Entregador;
import com.ufcg.psoft.commerce.service.entregador.EntregadorCriarService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorPegarService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(
        value = "/entregadores",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class EntregadorController {
    @Autowired
    EntregadorCriarService entregadorCriarService;
    @Autowired
    EntregadorPegarService entregadorPegarService;

    @PostMapping()
    public ResponseEntity<?> criaEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(entregadorCriarService.criaEntregador(entregadorPostPutRequestDto));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> pegaEntrgador(
            @PathVariable("id") Long idEntregador
    ){
        return ResponseEntity.status(HttpStatus.OK).body(entregadorPegarService.PegaEntregador(idEntregador));
    }

    @GetMapping
    public ResponseEntity<?> pegaTodosEntregadores(){
        List<EntregadorGetRequestDTO> entregadores = entregadorPegarService.PegaTodosEntregadores();
        return ResponseEntity.status(HttpStatus.OK).body(entregadores);
    }
}
