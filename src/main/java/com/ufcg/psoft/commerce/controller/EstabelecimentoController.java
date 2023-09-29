package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPutRequestDTO;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoAtualizarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoBuscarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoCadastrarService;
import com.ufcg.psoft.commerce.services.estabelecimento.EstabelecimentoDeletarService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/estabelecimentos", produces = MediaType.APPLICATION_JSON_VALUE)
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoCadastrarService estabelecimentoCadastrarService;

    @Autowired
    private EstabelecimentoDeletarService estabelecimentoDeletarService;

    @Autowired
    private EstabelecimentoAtualizarService estabelecimentoAtualizarService;

    @Autowired
    private EstabelecimentoBuscarService estabelecimentoBuscarService;

    @Autowired
    private SaborGetService saborGetService;

    @PostMapping("")
    public ResponseEntity<?> criarEstabelecimento(
            @Valid @RequestBody EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estabelecimentoCadastrarService.cadastrarEstabelecimento(estabelecimentoPostPutRequestDTO));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarEstabelecimento(@Valid @PathVariable Long id,
            @RequestParam("codigoAcesso") String codigoAcesso) {
        this.estabelecimentoDeletarService.deletarEstabelecimento(id, codigoAcesso);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarEstabelecimento(@PathVariable Long id,
            @RequestBody @Valid EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO,
            @Valid @RequestParam("codigoAcesso") String codigoAcesso) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.estabelecimentoAtualizarService.atualizarEstabelecimento(id, estabelecimentoPutRequestDTO,
                        codigoAcesso));
    }

    @GetMapping("/")
    public ResponseEntity<?> listarTodosEstaelecimentos() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(estabelecimentoBuscarService.listarTodos());
    }

    @GetMapping("/{id}/sabores")
    public ResponseEntity<?> buscarCardapio(@Valid @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(saborGetService.getAllCardapio(id));
    }

    @GetMapping("/{id}/cardapio/tipo")
    public ResponseEntity<?> buscarCardapioPorTipo(@Valid @PathVariable Long id,
    @RequestParam("tipo") String tipo
    ) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(saborGetService.getTipo(id, tipo));
    }

}