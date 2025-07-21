package com.lambda.APICasaDeJairo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambda.APICasaDeJairo.dto.EmpresaPerceiraDTO;
import com.lambda.APICasaDeJairo.service.EmpresaParceiraService;

import jakarta.validation.Valid;

//controle de serviços
@RestController
@RequestMapping("/api/empresa-parceira")
public class EmpresaParceiraController {


    @Autowired
    private EmpresaParceiraService service;

    @PostMapping
    public EmpresaPerceiraDTO criar(@RequestBody @Valid EmpresaPerceiraDTO dto) {
        return service.criarEmpresaParceira(dto);
    }

    @GetMapping
    public List<EmpresaPerceiraDTO> listar() {
        return service.listarEmpresaParceira();
    }
}
