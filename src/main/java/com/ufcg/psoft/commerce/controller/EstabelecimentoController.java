package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.estabelecimentos.EstabelecimentoPutRequestDTO;
import com.ufcg.psoft.commerce.model.Estabelecimento;
import com.ufcg.psoft.commerce.services.estabelecimentos.*;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/estabelecimentos")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoCadastrarService estabelecimentoCadastrarService;

    @Autowired
    private EstabelecimentoDeletarService estabelecimentoDeletarService;

    @Autowired
    private EstabelecimentoConsultarService estabelecimentoConsultarService;

    @Autowired
    private EstabelecimentoAtualizarService estabelecimentoAtualizarService;

    @Autowired
    private EstabelecimentoValidarCodigoService estabelecimentoValidarCodigoService;

    private ModelMapper modelMapper;

    public EstabelecimentoController(){
        this.modelMapper = new ModelMapper();
    }

    @PostMapping()
    public ResponseEntity<?> criarEstabelecimento(@Valid @RequestBody EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(estabelecimentoCadastrarService
                        .cadastrarEstabelecimento(modelMapper.map(estabelecimentoPostPutRequestDTO, Estabelecimento.class)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> deletarEstabelecimento(@Valid @PathVariable Long id, @RequestParam("codigoAcesso") String codigoAcesso) {
        ResponseEntity out = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(estabelecimentoValidarCodigoService.estabelecimentoValidaCodigoAcesso(id, codigoAcesso)){
            this.estabelecimentoDeletarService.deletarEstabelecimento(id);
            out = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return out;
    }

    @PutMapping("{id}")
    public ResponseEntity<?> atualizarEstabelecimento(@Valid @PathVariable Long id, @RequestBody EstabelecimentoPutRequestDTO estabelecimentoPutRequestDTO, @RequestParam("codigoAcesso") String codigoAcesso){
        //ResponseEntity out = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        if(estabelecimentoValidarCodigoService.estabelecimentoValidaCodigoAcesso(id, codigoAcesso)){
            return ResponseEntity.status(HttpStatus.OK)
                    .body(this.estabelecimentoAtualizarService.atualizarEstabelecimento(id, estabelecimentoPutRequestDTO));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

}


























