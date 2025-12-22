package com.lambda.apiCasaDeJairo.post;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lambda.apiCasaDeJairo.exceptions.RecursoNaoEncontradoException;

//regras de negocio do post de imagens
@Service
class PostImagemServiceImpl implements PostImagemService{

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
