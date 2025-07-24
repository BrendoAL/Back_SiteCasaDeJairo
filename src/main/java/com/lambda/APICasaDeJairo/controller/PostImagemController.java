package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.service.PostImagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
@Tag(name = "Post de Imagens", description = "Endpoints para upload e download de imagens")
public class PostImagemController {

    @Autowired
    private PostImagemService postImagemService;

    @Operation(summary = "Cria um novo post com imagem")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<PostImagem> criarPostImagem(
            @RequestParam String titulo,
            @RequestParam String conteudo,
            @RequestParam MultipartFile imagem) {
        try {
            PostImagem postImagem = postImagemService.salvarImagem(titulo, conteudo, imagem);
            return ResponseEntity.ok(postImagem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
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

