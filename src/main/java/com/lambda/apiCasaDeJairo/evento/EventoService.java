package com.lambda.apiCasaDeJairo.evento;

import java.io.IOException;
import java.util.List;

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
