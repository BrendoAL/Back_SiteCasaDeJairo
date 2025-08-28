package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.TransparenciaDTO;
import com.lambda.APICasaDeJairo.models.Transparencia;
import com.lambda.APICasaDeJairo.service.TransparenciaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transparencia")
@CrossOrigin(origins = "http://localhost:4200") // frontend Angular
public class TransparenciaController {

    private final TransparenciaService service;

    public TransparenciaController(TransparenciaService service) {
        this.service = service;
    }

    // GET público: lista todas as transparências
    @GetMapping
    public List<TransparenciaDTO> listar() {
        return service.listarTodos();
    }

    // POST → admin: cria um registro de transparência associado a uma imagem
    @PostMapping
    public ResponseEntity<Transparencia> criar(@RequestBody TransparenciaDTO dto) {
        Transparencia t = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    // GET imagem específica
    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long id) {
        byte[] img = service.getImagemDoRegistro(id);
        if (img.length == 0) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(img);
    }

    // PUT → admin: atualiza registro existente
    @PutMapping("/{id}")
    public ResponseEntity<Transparencia> atualizar(@PathVariable Long id, @RequestBody TransparenciaDTO dto) {
        Transparencia t = service.atualizar(id, dto);
        return ResponseEntity.ok(t);
    }

    // DELETE → admin: remove registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}