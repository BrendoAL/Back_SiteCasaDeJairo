package com.lambda.APICasaDeJairo.controller;


import java.util.List;

import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<EmpresaParceiraDTO> criar(@RequestBody @Valid EmpresaParceiraDTO dto) {
        try {
            EmpresaParceiraDTO empresa = service.criarEmpresaParceira(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(empresa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<EmpresaParceiraDTO>> listar() {
        List<EmpresaParceiraDTO> empresas = service.listarEmpresaParceira();
        return ResponseEntity.ok(empresas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaParceiraDTO> buscarPorId(@PathVariable Long id) {
        try {
            EmpresaParceiraDTO empresa = service.buscarPorId(id);
            return ResponseEntity.ok(empresa);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmpresaParceiraDTO> atualizar(@PathVariable Long id,
                                                        @RequestBody @Valid EmpresaParceiraDTO dto) {
        try {
            EmpresaParceiraDTO empresaAtualizada = service.atualizar(id, dto);
            return ResponseEntity.ok(empresaAtualizada);
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RecursoNaoEncontradoException e) {
            return ResponseEntity.notFound().build();
        }
    }
}