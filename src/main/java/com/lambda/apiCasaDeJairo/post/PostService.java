package com.lambda.apiCasaDeJairo.post;

import java.util.List;

public interface PostService {
    Post createPost(PostDTO dto);
    List<PostDTO> listarTodos();
    byte[] getImagemDoPost(Long postId);
}
