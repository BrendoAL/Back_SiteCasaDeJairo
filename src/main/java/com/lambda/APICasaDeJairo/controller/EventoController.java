package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService service;

    @PostMapping
    public EventoDTO criar(@RequestBody @Valid EventoDTO dto) {
        return service.criar(dto);
    }

    @GetMapping
    public List<EventoDTO> listar() {
        return service.listar();
    }

    @PutMapping("/{id}")
    public EventoDTO atualizar(@PathVariable Long id, @RequestBody @Valid EventoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}
