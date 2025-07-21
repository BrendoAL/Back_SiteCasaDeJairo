package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.models.PostImagem;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface PostImagemService {
    PostImagem salvarImagem(String titulo, String conteudo, MultipartFile imagem) throws IOException;
    byte[] getImagemById(Long id) throws Exception;
}
