package com.lambda.apiCasaDeJairo.transparencia;

import org.springframework.web.multipart.MultipartFile;
import java.util.List;

public interface TransparenciaService {

    Transparencia criar(TransparenciaDTO dto);
    List<TransparenciaDTO> listarTodos();
    byte[] getImagemDoRegistro(Long id);
    Transparencia atualizar(Long id, TransparenciaDTO dto);
    void deletar(Long id);
    Transparencia criarComImagem(String titulo, String descricao, String data, MultipartFile imagem) throws Exception;
    Transparencia atualizarComImagem(Long id, String titulo, String descricao, String data, MultipartFile imagem);
    byte[] getImagemPorId(Long imagemId);
}
