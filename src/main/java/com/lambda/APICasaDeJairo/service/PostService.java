package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.models.Post;
import com.lambda.APICasaDeJairo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//regras de negocio do post de publicações
@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    public Post createPost(PostDTO dto) {
        Post post = new Post();
        post.setTitulo(dto.getTitulo());
        post.setConteudo(dto.getConteudo());
        return postRepository.save(post);
    }
}

