package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.services.entregador.EntregadorAtualizarService;
import com.ufcg.psoft.commerce.services.entregador.EntregadorCriarService;
import com.ufcg.psoft.commerce.services.entregador.EntregadorDelatarService;
import com.ufcg.psoft.commerce.services.entregador.EntregadorPegarService;
import jakarta.validation.Valid;
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
    @Autowired
    EntregadorAtualizarService entregadorAtualizarService;
    @Autowired
    EntregadorDelatarService entregadorDeletarService;

    @PostMapping()
    public ResponseEntity<?> criaEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDto
    ){
        return ResponseEntity.status(HttpStatus.CREATED).body(entregadorCriarService.criaEntregador(entregadorPostPutRequestDto));
    }

    @GetMapping("{idEntregador}")
    public ResponseEntity<?> pegaEntrgador(
            @PathVariable("idEntregador") Long idEntregador
    ){
        return ResponseEntity.status(HttpStatus.OK).body(entregadorPegarService.pegaEntregador(idEntregador));
    }

    @GetMapping
    public ResponseEntity<?> pegaTodosEntregadores(){
        List<EntregadorGetRequestDTO> entregadores = entregadorPegarService.PegaTodosEntregadores();
        return ResponseEntity.status(HttpStatus.OK).body(entregadores);
    }

    @PutMapping("{idEntregador}")
    public ResponseEntity<?> atualizaEntregador(
            @RequestBody @Valid EntregadorPostPutRequestDTO entregadorPostPutRequestDTO,
            @PathVariable("idEntregador") Long idEntregador,
            @RequestParam("codigoAcesso") String codigoAcesso
    ){
        return ResponseEntity.status(HttpStatus.OK).body(entregadorAtualizarService.atualizaEntregador(entregadorPostPutRequestDTO
        , idEntregador, codigoAcesso));
    }

    @DeleteMapping("{idEntregador}")
    public ResponseEntity<?> deletaEntregador(
            @PathVariable("idEntregador") Long idEntregador,
            @RequestParam("codigoAcesso") String codigoAcesso
    ){
        entregadorDeletarService.deletaEntregador(idEntregador, codigoAcesso);
        return ResponseEntity.status((HttpStatus.NO_CONTENT)).body("");
    }
}
