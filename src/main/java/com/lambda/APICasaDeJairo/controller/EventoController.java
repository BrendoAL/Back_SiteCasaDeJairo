package com.lambda.APICasaDeJairo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    // Criar novo evento
    @PostMapping
    public ResponseEntity<EventoDTO> criar(@RequestBody EventoDTO dto) {
        EventoDTO criado = service.criar(dto);
        return ResponseEntity.ok(criado);
    }

    // Listar todos os eventos
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

    // Atualizar evento
    @PutMapping("/{id}")
    public ResponseEntity<EventoDTO> atualizar(@PathVariable Long id, @RequestBody EventoDTO dto) {
        EventoDTO atualizado = service.atualizar(id, dto);
        return ResponseEntity.ok(atualizado);
    }

    // Deletar evento
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
