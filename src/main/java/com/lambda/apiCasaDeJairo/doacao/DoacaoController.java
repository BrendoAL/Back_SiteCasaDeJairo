package com.lambda.apiCasaDeJairo.doacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/doacoes")
class DoacaoController {

    @Autowired
    private DoacaoService service;

    @PostMapping
    DoacaoDTO registrar(@RequestBody @Valid DoacaoDTO dto) {
        return service.criarDoacao(dto);
    }

    @GetMapping
    List<DoacaoDTO> listar() {
        return service.listarDoacao();
    }
}
