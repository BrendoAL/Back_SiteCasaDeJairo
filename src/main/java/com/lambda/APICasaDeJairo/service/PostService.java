package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.models.Post;

import java.util.List;

public interface PostService {
    Post createPost(PostDTO dto);
    List<PostDTO> listarTodos();
    byte[] getImagemDoPost(Long postId);
}
