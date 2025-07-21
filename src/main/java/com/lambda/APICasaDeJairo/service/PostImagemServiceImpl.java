package com.lambda.APICasaDeJairo.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.repository.PostImagemRepository;

@Service
public class PostImagemServiceImpl implements PostImagemService{

    @Autowired
    private PostImagemRepository repository;

    public PostImagem salvarImagem(String titulo, String conteudo, MultipartFile imagem) throws IOException {
        PostImagem post = new PostImagem();
        post.setTitulo(titulo);
        post.setConteudo(conteudo);
        post.setImagem(imagem.getBytes()); // salva o conteúdo binário no banco

        return repository.save(post);
    }

    public byte[] getImagemById(Long id) throws RecursoNaoEncontradoException {
        PostImagem post = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Post não encontrado"));
        return post.getImagem();
    }
}
