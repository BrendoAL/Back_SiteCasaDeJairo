package com.lambda.APICasaDeJairo.service;

import java.io.IOException;
import java.util.List;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import org.springframework.web.multipart.MultipartFile;

/**
 * Interface que define os métodos de negócio para gerenciamento de eventos.
 * Inclui operações para criar, listar, atualizar e deletar eventos.
 * Utiliza objetos do tipo EventoDTO para transferência de dados.
 */

public interface EventoService {
    EventoDTO criar(EventoDTO dto, MultipartFile imagem) throws IOException;
    List<EventoDTO> listar();
    EventoDTO atualizar(Long id, EventoDTO dto, MultipartFile imagem) throws IOException;
    void deletar(Long id);
    byte[] getImagemById(Long id) throws IOException;
    EventoDTO buscarPorId(Long id);
}
