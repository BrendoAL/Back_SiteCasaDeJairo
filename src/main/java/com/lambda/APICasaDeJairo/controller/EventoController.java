package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.service.EmailService;
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
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/eventos")
@CrossOrigin(origins = {"http://localhost:4200", "https://casadejairo.online/"})
public class EventoController {

    @Autowired
    private EventoService eventoService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarEvento(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestParam String local,
            @RequestParam(required = false) MultipartFile imagem) {

        try {
            // Criar DTO e salvar evento
            EventoDTO dto = new EventoDTO(null, titulo, descricao, LocalDate.parse(data), local, null);
            EventoDTO criado = eventoService.criar(dto, imagem);

            if (criado.getId() != null && imagem != null && !imagem.isEmpty()) {
                criado.setImagemUrl("/api/eventos/imagem/" + criado.getId());
            }

            // Notifica voluntários usando o Service (usa template de email)
            eventoService.notificarUsuariosSobreEvento(criado.getId()); // passamos apenas o ID para buscar Evento real no Service

            return ResponseEntity.ok(criado);

        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", "Formato de data inválido. Use YYYY-MM-DD",
                    "timestamp", LocalDateTime.now(),
                    "status", 400
            ));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", "Erro interno: " + e.getMessage(),
                    "timestamp", LocalDateTime.now(),
                    "status", 400
            ));
        }
    }

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

            if (atualizado.getId() != null) {
                if (imagem != null && !imagem.isEmpty()) {
                    atualizado.setImagemUrl("/api/eventos/imagem/" + atualizado.getId());
                } else if (atualizado.getImagemUrl() == null) {
                    try {
                        eventoService.getImagemById(id);
                        atualizado.setImagemUrl("/api/eventos/imagem/" + id);
                    } catch (Exception ignored) {}
                }
            }

            return ResponseEntity.ok(atualizado);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", e.getMessage(),
                    "timestamp", LocalDateTime.now(),
                    "status", 400
            ));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            eventoService.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/imagem/{id}")
    public ResponseEntity<byte[]> getImagemEvento(@PathVariable Long id) {
        try {
            byte[] imagem = eventoService.getImagemById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setCacheControl("public, max-age=31536000"); // 1 ano
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}