package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;
import com.lambda.APICasaDeJairo.service.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

//regras de negocio
@RestController
@RequestMapping("/api/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoService service;

    @PostMapping
    public DoacaoDTO registrar(@RequestBody @Valid DoacaoDTO dto) {
        return service.criarDoacao(dto);
    }

    @GetMapping
    public List<DoacaoDTO> listar() {
        return service.listarDoacao();
    }
}
