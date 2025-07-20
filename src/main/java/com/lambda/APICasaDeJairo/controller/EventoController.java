package com.lambda.APICasaDeJairo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.service.EventoService;

import jakarta.validation.Valid;

/**
 * Controller responsável por expor endpoints da API para operações com eventos.
 * Permite criar, listar, atualizar e deletar eventos através de requisições HTTP.
 * Recebe e retorna objetos do tipo EventoDTO.
 */

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
