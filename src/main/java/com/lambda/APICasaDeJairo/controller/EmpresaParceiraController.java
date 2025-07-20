package com.lambda.APICasaDeJairo.controller;


import com.lambda.APICasaDeJairo.dto.EmpresaPerceiraDTO;
import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.service.EmpresaParceiraService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
