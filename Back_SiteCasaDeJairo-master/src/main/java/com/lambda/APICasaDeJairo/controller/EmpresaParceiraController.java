package com.lambda.APICasaDeJairo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambda.APICasaDeJairo.dto.EmpresaParceiraDTO;
import com.lambda.APICasaDeJairo.service.EmpresaParceiraService;

import jakarta.validation.Valid;

//controller para o service
@RestController
@RequestMapping("/api/empresa-parceira")
public class EmpresaParceiraController {

    @Autowired
    private EmpresaParceiraService service;

    @PostMapping
    public EmpresaParceiraDTO criar(@RequestBody @Valid EmpresaParceiraDTO dto) {
        return service.criarEmpresaParceira(dto);
    }

    @GetMapping
    public List<EmpresaParceiraDTO> listar() {
        return service.listarEmpresaParceira();
    }
}
