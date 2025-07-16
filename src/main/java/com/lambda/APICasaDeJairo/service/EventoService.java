package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import java.util.List;

public interface  EventoService {
    EventoDTO criar(EventoDTO dto);

    List<EventoDTO> listar();

    EventoDTO atualizar(Long id, EventoDTO dto);

    void deletar(Long id);
}
