package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.services.associacao.AssociacaoCreateService;
import com.ufcg.psoft.commerce.services.associacao.AssociacaoPegarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = "/associacao",
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class AssociacaoController {

    @Autowired
    AssociacaoCreateService associacaoCreateService;

    @Autowired
    AssociacaoPegarService associacaoPegarService;

    @PostMapping()
    public ResponseEntity<?> create(
            @RequestParam("entregadorId") Long entregadorId,
            @RequestParam("estabelecimentoId") Long estabelecimentoId,
            @RequestParam("codigoAcesso") String codigoAcesso
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(associacaoCreateService.create(entregadorId, codigoAcesso, estabelecimentoId));

    }

    @GetMapping()
    public ResponseEntity<?> create(@RequestParam("id") Long associacaoId) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(associacaoPegarService.pegarAssociacao(associacaoId));

    }
}
