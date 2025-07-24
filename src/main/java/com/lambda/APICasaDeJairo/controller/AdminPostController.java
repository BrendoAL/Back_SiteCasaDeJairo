package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.models.Post;
import com.lambda.APICasaDeJairo.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/posts")
public class AdminPostController {

    @Autowired
    private PostService postService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createPost(@RequestBody PostDTO postDTO) {
        Post post = postService.createPost(postDTO);
        return ResponseEntity.ok(post);
    }
}

