package com.ufcg.psoft.commerce.controller;

import com.ufcg.psoft.commerce.services.sabor.SaborCadastraService;
import com.ufcg.psoft.commerce.services.sabor.SaborDeleteService;
import com.ufcg.psoft.commerce.services.sabor.SaborGetService;
import com.ufcg.psoft.commerce.services.sabor.SaborUpdateService;
import com.ufcg.psoft.commerce.dto.sabores.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.model.Sabor;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/sabores", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaborV1RestController {

        @Autowired
        private ModelMapper modelMapper;

        @Autowired
        private SaborCadastraService saborCadastraService;

        @Autowired
        private SaborGetService saborGetService;

        @Autowired
        private SaborDeleteService saborDeleteService;

        @Autowired
        private SaborUpdateService saborUpdateService;

        @PostMapping()
        public ResponseEntity<?> criarSabor(@Valid @RequestBody SaborPostPutRequestDTO saborPostPutRequestDTO,
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(saborCadastraService.cadastrarSabor(
                                                saborPostPutRequestDTO,
                                                Long.parseLong(estabelecimentoId),
                                                estabelecimentoCodigoAcesso));
        }

        @GetMapping()
        public ResponseEntity<?> buscarTodosOsSabores(
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(saborGetService.getAll(Long.parseLong(estabelecimentoId),
                                                estabelecimentoCodigoAcesso));
        }

        @GetMapping("/{id}")
        public ResponseEntity<?> buscarPorId(@PathVariable("id") Long id,
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(saborGetService.getSaborById(id, Long.parseLong(estabelecimentoId),
                                                estabelecimentoCodigoAcesso));

        }

        @DeleteMapping("")
        public ResponseEntity<?> deletarPorId(@RequestParam("saborId") String saborId,
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {
                saborDeleteService.delete(Long.parseLong(saborId), Long.parseLong(estabelecimentoId),
                                estabelecimentoCodigoAcesso);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        @PutMapping("")
        public ResponseEntity<?> updateSaborPorId(@Valid @RequestBody SaborPostPutRequestDTO saborPostPutRequestDTO,
                        @RequestParam("saborId") String saborId,
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(saborUpdateService.updateById(saborPostPutRequestDTO, Long.parseLong(saborId),
                                                Long.parseLong(estabelecimentoId), estabelecimentoCodigoAcesso));
        }

        @PutMapping("/{id}/{value}")
        public ResponseEntity<?> updateSaborPorIdDisponibilidade(
                        @Valid @RequestBody SaborPostPutRequestDTO saborPostPutRequestDTO,
                        @RequestParam("saborId") String saborId,
                        @RequestParam("estabelecimentoId") String estabelecimentoId,
                        @RequestParam("estabelecimentoCodigoAcesso") String estabelecimentoCodigoAcesso,
                        @RequestParam("disponibilidade") String disponibilidade) {
                return ResponseEntity.status(HttpStatus.OK)
                                .body(saborUpdateService.updateByIdDisponibilidade(saborPostPutRequestDTO,
                                                Long.parseLong(saborId), Long.parseLong(estabelecimentoId),
                                                estabelecimentoCodigoAcesso, Boolean.parseBoolean(disponibilidade)));
        }

}