package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.controller.PostImagemController;
import com.lambda.APICasaDeJairo.service.PostImagemService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.lambda.APICasaDeJairo.models.PostImagem;

//Teste unitário da classe PostImagem
@WebMvcTest(PostImagemController.class)
public class PostImagemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private PostImagemService postImagemService;

    @Test
    public void testSalvarImagem() throws Exception {
        // simula uma imagem
        MockMultipartFile imagem = new MockMultipartFile(
                "imagem", "foto.png", MediaType.IMAGE_PNG_VALUE,
                "imagem de teste".getBytes());


        // simulando retorno do service
        PostImagem testePost = new PostImagem();
        testePost.setId(1L);
        testePost.setTitulo("Título Teste");
        testePost.setConteudo("Conteúdo Teste");
        testePost.setImagem(imagem.getBytes());

        when(postImagemService.salvarImagem(any(), any(), any())).thenReturn(testePost);

        mockMvc.perform(multipart("/posts")
                        .file(imagem)
                        .param("titulo", "Título Teste")
                        .param("conteudo", "Conteúdo Teste"))
                .andExpect(status().isOk());
    }
}

