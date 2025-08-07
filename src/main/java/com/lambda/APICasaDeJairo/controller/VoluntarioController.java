package com.lambda.APICasaDeJairo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.service.VoluntarioService;

import jakarta.validation.Valid;

//controller para o service
@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @PostMapping
    public VoluntarioDTO criar(@RequestBody @Valid VoluntarioDTO dto) {
        return service.criarVoluntario(dto);
    }

    @GetMapping
    public List<VoluntarioDTO> listar() {
        return service.listarVoluntarios();
    }
}
