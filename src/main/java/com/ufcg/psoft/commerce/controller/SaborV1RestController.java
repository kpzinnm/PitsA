package com.ufcg.psoft.commerce.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import com.ufcg.psoft.commerce.services.sabor.SaborCadastraService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/api/v1/sabores", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaborV1RestController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SaborCadastraService saborCadastraService;

    @PostMapping()
    public ResponseEntity<?> criarSabor(@RequestBody @Valid SaborPostPutRequestDTO saborPostPutRequestDTO,
        @RequestParam("estabelecimentoId") String estabelecimentoId,
        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(saborCadastraService.cadastrarSabor(
                    modelMapper.map(saborPostPutRequestDTO, Sabor.class),
                    Long.parseLong(estabelecimentoId), 
                    estabelecimentoCodigoAcesso));
    }
}