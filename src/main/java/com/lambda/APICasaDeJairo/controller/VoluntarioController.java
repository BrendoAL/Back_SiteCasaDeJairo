package com.lambda.APICasaDeJairo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.service.VoluntarioService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/voluntarios")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @PostMapping
    public ResponseEntity<?> criar(@RequestBody @Valid VoluntarioDTO dto) {
        try {
            VoluntarioDTO voluntarioSalvo = service.criarVoluntario(dto);
            return ResponseEntity.ok(voluntarioSalvo);
        } catch (RuntimeException e) {
            // Retorna erro 400 para erros de validação (ex: email já existe)
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            // Retorna erro 500 para erros internos
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno do servidor"));
        }
    }

    @GetMapping
    public ResponseEntity<List<VoluntarioDTO>> listar() {
        try {
            List<VoluntarioDTO> voluntarios = service.listarVoluntarios();
            return ResponseEntity.ok(voluntarios);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletar(@PathVariable Long id) {
        try {
            service.deletarVoluntario(id);
            return ResponseEntity.ok(Map.of("message", "Voluntário deletado com sucesso"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Erro interno do servidor"));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<VoluntarioDTO> buscarPorEmail(@PathVariable String email) {
        try {
            VoluntarioDTO voluntario = service.buscarPorEmail(email);
            if (voluntario != null) {
                return ResponseEntity.ok(voluntario);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
