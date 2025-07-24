package com.lambda.APICasaDeJairo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Evento;
import com.lambda.APICasaDeJairo.repository.EventoRepository;

/**
 * Implementação da interface EventoService que gerencia a lógica de negócio dos
 * eventos.
 * Responsável por criar, listar, atualizar e deletar eventos no banco de dados
 * usando o EventoRepository. Faz a conversão entre a entidade Evento e o
 * EventoDTO.
 */

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;

    @Override
    public EventoDTO criar(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        Evento salvo = repository.save(evento);

        return new EventoDTO(
                salvo.getTitulo(),
                salvo.getDescricao(),
                salvo.getData(),
                salvo.getLocal());
    }

    @Override
    public List<EventoDTO> listar() {
        return repository.findAllByOrderByDataAsc().stream()
                .map(e -> new EventoDTO(
                        e.getTitulo(),
                        e.getDescricao(),
                        e.getData(),
                        e.getLocal()))
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO atualizar(Long id, EventoDTO dto) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        Evento atualizado = repository.save(evento);

        return new EventoDTO(
                atualizado.getTitulo(),
                atualizado.getDescricao(),
                atualizado.getData(),
                atualizado.getLocal());
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado");
        }
        repository.deleteById(id);
    }
}
