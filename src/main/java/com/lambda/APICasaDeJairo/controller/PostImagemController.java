package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.service.PostImagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

//controller para o service
@RestController
@RequestMapping("/api/postImagem")
@CrossOrigin(origins = "http://localhost:4200")
@Tag(name = "Post de Imagens", description = "Endpoints para upload e download de imagens")
public class PostImagemController {

    @Autowired
    private PostImagemService postImagemService;


    @Operation(summary = "Cria um novo post com imagem")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarPostImagem(
            @RequestParam String titulo,
            @RequestParam String conteudo,
            @RequestParam MultipartFile imagem) {
        try {
            PostImagem postImagem = postImagemService.salvarImagem(titulo, conteudo, imagem);
            return ResponseEntity.ok(postImagem);
        } catch (Exception e) {
            // Retorna erro com mensagem e timestamp para facilitar o debug
            Map<String, Object> erro = new HashMap<>();
            erro.put("mensagem", "Erro inesperado: " + e.getMessage());
            erro.put("timestamp", java.time.LocalDateTime.now());
            erro.put("status", HttpStatus.BAD_REQUEST.value());

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }
    }

    @Operation(summary = "Recupera a imagem de um post por ID")
    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPostImagem(@PathVariable Long id) {
        try {
            byte[] imagem = postImagemService.getImagemById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

