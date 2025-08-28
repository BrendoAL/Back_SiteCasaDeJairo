package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.models.Post;
import com.lambda.APICasaDeJairo.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@CrossOrigin("*")
@Tag(name = "Posts de Transparência", description = "Endpoints para gerenciar publicações da ONG")
public class PostController {

    @Autowired
    private PostService postService;

    @Operation(summary = "Criar um novo post")
    @PostMapping
    public ResponseEntity<Post> criarPost(@RequestBody PostDTO dto) {
        Post novoPost = postService.createPost(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPost);
    }

    @Operation(summary = "Listar todos os posts")
    @GetMapping
    public ResponseEntity<List<PostDTO>> listarPosts() {
        return ResponseEntity.ok(postService.listarTodos());
    }

    @Operation(summary = "Obter imagem de um post pelo ID")
    @GetMapping("/{postId}/imagem")
    public ResponseEntity<byte[]> getImagemDoPost(@PathVariable Long postId) {
        byte[] imagem = postService.getImagemDoPost(postId);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imagem, headers, HttpStatus.OK);
    }
}
