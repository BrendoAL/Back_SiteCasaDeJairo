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

    // Listar todas as transparências
    @GetMapping
    public List<TransparenciaDTO> listar() {
        return service.listarTodos();
    }

    // Criar transparência com imagem
    @PostMapping(value = "/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarComImagem(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        try {
            // Validações básicas
            if (titulo == null || titulo.trim().isEmpty())
                return ResponseEntity.badRequest().body(Map.of("mensagem", "Título é obrigatório"));
            if (descricao == null || descricao.trim().isEmpty())
                return ResponseEntity.badRequest().body(Map.of("mensagem", "Descrição é obrigatória"));
            if (data == null || data.trim().isEmpty())
                return ResponseEntity.badRequest().body(Map.of("mensagem", "Data é obrigatória"));
            LocalDate.parse(data); // valida formato da data

            if (imagem != null && !imagem.isEmpty()) {
                if (imagem.getSize() > 10 * 1024 * 1024)
                    return ResponseEntity.badRequest().body(Map.of("mensagem", "Imagem muito grande. Máximo 10MB"));
                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/"))
                    return ResponseEntity.badRequest().body(Map.of("mensagem", "Arquivo deve ser uma imagem"));
            }

            Transparencia t = service.criarComImagem(titulo, descricao, data, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(t);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro ao processar imagem: " + e.getMessage()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("mensagem", "Erro interno: " + e.getMessage()));
        }
    }

    // Atualizar transparência com imagem
    @PutMapping(value = "/{id}/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarComImagem(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        try {
            Transparencia t = service.atualizarComImagem(id, titulo, descricao, data, imagem);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(Map.of("mensagem", "Erro ao atualizar: " + e.getMessage()));
        }
    }

    // Buscar imagem por ID
    @GetMapping("/imagem/{postImagemId}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long postImagemId) {
        try {
            byte[] img = service.getImagemPorId(postImagemId);
            if (img == null || img.length == 0) {
                return ResponseEntity.notFound().build();
            }
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setCacheControl("public, max-age=31536000");
            return ResponseEntity.ok().headers(headers).body(img);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    // Deletar transparência
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        try {
            service.deletar(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }
}