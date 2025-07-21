package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.service.PostImagemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
public class PostImagemController {

    @Autowired
    private PostImagemService postImagemService;

    @PostMapping
    public ResponseEntity<PostImagem> criarPostImagem(
            @RequestParam String titulo,
            @RequestParam String conteudo,
            @RequestParam MultipartFile imagem) {
        try{
            PostImagem postImagem = postImagemService.salvarImagem(titulo, conteudo, imagem);
            return ResponseEntity.ok(postImagem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getPostImagem(@PathVariable Long id) {
        try {
            byte[] imagem = postImagemService.getImagemById(id);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // ou IMAGE_PNG
            return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
