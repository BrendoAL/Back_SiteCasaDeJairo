package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Post;
import com.lambda.APICasaDeJairo.repository.PostRepository;
import com.lambda.APICasaDeJairo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository; // ⬅️ Adicione isso

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postDTO);
        return ResponseEntity.ok(post);
    }

    @GetMapping
    public List<PostDTO> listarTodos() {
        return postRepository.findAll().stream() //
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setTitulo(post.getTitulo());
                    dto.setConteudo(post.getConteudo());

                    if (post.getPostImagem() != null) {
                        dto.setPostImagemId(post.getPostImagem().getId());
                    }
                    return dto;
                })
                .toList();
    }

    @GetMapping("/{id}/imagem")
    public ResponseEntity<byte[]> getImagemDoPost(@PathVariable Long id) {
        Post post = postRepository.findById(id) // ⬅️ Aqui também
                .orElseThrow(() -> new RecursoNaoEncontradoException("Post não encontrado"));
        if (post.getPostImagem() == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(post.getPostImagem().getImagem());
    }
}

