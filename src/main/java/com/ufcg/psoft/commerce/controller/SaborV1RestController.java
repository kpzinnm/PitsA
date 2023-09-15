package com.ufcg.psoft.commerce.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/v1/sabores", produces = MediaType.APPLICATION_JSON_VALUE)
public class SaborV1RestController {

    @Autowired
    ModelMapper modelMapper;
}