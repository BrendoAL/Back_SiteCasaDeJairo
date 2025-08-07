package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.PostDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Post;
import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.repository.PostImagemRepository;
import com.lambda.APICasaDeJairo.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

//regras de negocio do post de publicações
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostImagemRepository postImagemRepository;

    @Override
    public Post createPost(PostDTO dto) {
        Post post = new Post();
        post.setTitulo(dto.getTitulo());
        post.setConteudo(dto.getConteudo());

        if (dto.getPostImagemId() != null) {
            PostImagem imagem = postImagemRepository.findById(dto.getPostImagemId())
                    .orElseThrow(() -> new RecursoNaoEncontradoException("Imagem não encontrada"));
            post.setPostImagem(imagem);
        }

        return postRepository.save(post);
    }

    @Override
    public List<PostDTO> listarTodos() {
        return postRepository.findAll().stream()
                .map(post -> {
                    PostDTO dto = new PostDTO();
                    dto.setTitulo(post.getTitulo());
                    dto.setConteudo(post.getConteudo());
                    if (post.getPostImagem() != null) {
                        dto.setPostImagemId(post.getPostImagem().getId());
                    }
                    return dto;
                }).toList();
    }

    @Override
    public byte[] getImagemDoPost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Post não encontrado"));

        if (post.getPostImagem() == null) {
            throw new RecursoNaoEncontradoException("Imagem não encontrada para este post");
        }

        return post.getPostImagem().getImagem();
    }
}
