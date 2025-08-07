package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.EventoDTO;

/**
 * Interface que define os métodos de negócio para gerenciamento de eventos.
 * Inclui operações para criar, listar, atualizar e deletar eventos.
 * Utiliza objetos do tipo EventoDTO para transferência de dados.
 */

public interface  EventoService {
    EventoDTO criar(EventoDTO dto);

    List<EventoDTO> listar();

    EventoDTO atualizar(Long id, EventoDTO dto);

    void deletar(Long id);
}
