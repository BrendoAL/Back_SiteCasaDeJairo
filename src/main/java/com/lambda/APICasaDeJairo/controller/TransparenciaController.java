package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.TransparenciaDTO;
import com.lambda.APICasaDeJairo.models.Transparencia;
import com.lambda.APICasaDeJairo.service.TransparenciaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/transparencia")
@CrossOrigin(origins = {"http://localhost:4200", "https://casadejairo.online/"})
public class TransparenciaController {

    private final TransparenciaService service;

    public TransparenciaController(TransparenciaService service) {
        this.service = service;
    }

    @GetMapping
    public List<TransparenciaDTO> listar() {
        return service.listarTodos();
    }

    @PostMapping
    public ResponseEntity<Transparencia> criar(@RequestBody TransparenciaDTO dto) {
        try {
            Transparencia t = service.criar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(t);
        } catch (Exception e) {
            System.err.println("Erro ao criar transparência: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarComImagem(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        System.out.println("=== DEBUG TRANSPARENCIA COM IMAGEM ===");
        System.out.println("Título: " + titulo);
        System.out.println("Descrição: " + descricao);
        System.out.println("Data: " + data);
        System.out.println("Imagem: " + (imagem != null ? imagem.getOriginalFilename() + " (" + imagem.getSize() + " bytes)" : "null"));

        try {
            if (titulo == null || titulo.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "mensagem", "Título é obrigatório",
                        "campo", "titulo"
                ));
            }

            if (descricao == null || descricao.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "mensagem", "Descrição é obrigatória",
                        "campo", "descricao"
                ));
            }

            if (data == null || data.trim().isEmpty()) {
                return ResponseEntity.badRequest().body(Map.of(
                        "mensagem", "Data é obrigatória",
                        "campo", "data"
                ));
            }

            // Tentar parsear a data para validar
            try {
                LocalDate.parse(data);
            } catch (DateTimeParseException e) {
                return ResponseEntity.badRequest().body(Map.of(
                        "mensagem", "Formato de data inválido. Use YYYY-MM-DD",
                        "campo", "data"
                ));
            }

            if (imagem != null && !imagem.isEmpty()) {
                if (imagem.getSize() > 10 * 1024 * 1024) { // 10MB
                    return ResponseEntity.badRequest().body(Map.of(
                            "mensagem", "Imagem muito grande. Máximo 10MB",
                            "campo", "imagem"
                    ));
                }

                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.badRequest().body(Map.of(
                            "mensagem", "Arquivo deve ser uma imagem",
                            "campo", "imagem"
                    ));
                }
            }

            Transparencia t = service.criarComImagem(titulo, descricao, data, imagem);
            System.out.println("Transparência criada com sucesso: ID = " + t.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(t);

        } catch (IOException e) {
            System.err.println("Erro de IO ao criar transparência: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", "Erro ao processar imagem: " + e.getMessage()
            ));
        } catch (Exception e) {
            System.err.println("Erro geral ao criar transparência: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", "Erro interno: " + e.getMessage()
            ));
        }
    }

    @PutMapping(value = "/{id}/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarComImagem(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        System.out.println("=== DEBUG ATUALIZAR TRANSPARENCIA ===");
        System.out.println("ID: " + id);
        System.out.println("Título: " + titulo);
        System.out.println("Imagem: " + (imagem != null ? imagem.getOriginalFilename() : "null"));

        try {
            Transparencia t = service.atualizarComImagem(id, titulo, descricao, data, imagem);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            System.err.println("Erro ao atualizar transparência: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of(
                    "mensagem", "Erro ao atualizar: " + e.getMessage()
            ));
        }
    }

    @GetMapping("/imagem/{postImagemId}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long postImagemId) {
        try {
            byte[] img = service.getImagemPorId(postImagemId);
            if (img == null || img.length == 0) {
                System.out.println("Imagem não encontrada para ID: " + postImagemId);
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setCacheControl("public, max-age=31536000"); // Cache por 1 ano
            return ResponseEntity.ok().headers(headers).body(img);
        } catch (Exception e) {
            System.err.println("Erro ao buscar imagem ID " + postImagemId + ": " + e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Transparencia> atualizar(@PathVariable Long id, @RequestBody TransparenciaDTO dto) {
        try {
            Transparencia t = service.atualizar(id, dto);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}