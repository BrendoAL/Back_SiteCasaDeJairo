package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.TransparenciaDTO;
import com.lambda.APICasaDeJairo.models.Transparencia;
import java.util.List;

public interface TransparenciaService {
    Transparencia criar(TransparenciaDTO dto);       // cria novo registro
    List<TransparenciaDTO> listarTodos();            // retorna DTOs para o frontend
    byte[] getImagemDoRegistro(Long id);             // retorna imagem binária
    Transparencia atualizar(Long id, TransparenciaDTO dto); // atualiza registro existente
    void deletar(Long id);                           // deleta registro
}
