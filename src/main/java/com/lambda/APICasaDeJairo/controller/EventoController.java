package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping
    public ResponseEntity<EventoDTO> criar(@RequestBody EventoDTO dto) {
        try {
            EventoDTO criado = eventoService.criar(dto, null);
            return ResponseEntity.ok(criado);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    // Criar evento com upload de imagem
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarEventoComImagem(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestParam String local,
            @RequestParam(required = false) MultipartFile imagem) {
        try {
            EventoDTO dto = new EventoDTO(null, titulo, descricao, LocalDate.parse(data), local, null);
            EventoDTO criado = eventoService.criar(dto, imagem);

            if (criado.getId() != null && imagem != null && !imagem.isEmpty()) {
                criado.setImagemUrl("/api/eventos/imagem/" + criado.getId());
            }

            return ResponseEntity.ok(criado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", e.getMessage(),
                    "timestamp", LocalDateTime.now(),
                    "status", 400
            ));
        }
    }

    // ================= LISTAR =================
    @GetMapping
    public ResponseEntity<List<EventoDTO>> listar() {
        return ResponseEntity.ok(eventoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO> buscarEvento(@PathVariable Long id) {
        try {
            EventoDTO evento = eventoService.buscarPorId(id);
            return ResponseEntity.ok(evento);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ================= ATUALIZAR =================
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarComImagem(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestParam String local,
            @RequestParam(required = false) MultipartFile imagem) {
        try {
            EventoDTO dto = new EventoDTO(id, titulo, descricao, LocalDate.parse(data), local, null);
            EventoDTO atualizado = eventoService.atualizar(id, dto, imagem);
            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", e.getMessage(),
                    "timestamp", LocalDateTime.now(),
                    "status", 400
            ));
        }
    }

    // ================= DELETAR =================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ================= IMAGENS =================
    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> getImagemEvento(@PathVariable Long id) {
        try {
            byte[] imagem = eventoService.getImagemById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}


