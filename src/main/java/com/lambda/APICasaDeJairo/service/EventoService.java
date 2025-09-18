package com.lambda.APICasaDeJairo.service;

import java.io.IOException;
import java.util.List;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.models.Evento;
import org.springframework.web.multipart.MultipartFile;

public interface EventoService {
    EventoDTO criar(EventoDTO dto, MultipartFile imagem) throws IOException;

    List<EventoDTO> listar();

    EventoDTO atualizar(Long id, EventoDTO dto, MultipartFile imagem) throws IOException;

    void deletar(Long id);

    void notificarUsuariosSobreEvento(Long eventoId);

    byte[] getImagemById(Long id) throws IOException;

    EventoDTO buscarPorId(Long id);
}
