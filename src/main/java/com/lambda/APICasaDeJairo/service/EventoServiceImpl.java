package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.models.Evento;
import com.lambda.APICasaDeJairo.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;

    @Override
    public EventoDTO criar(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHora(dto.getDataHora());
        evento.setLocal(dto.getLocal());

        Evento salvo = repository.save(evento);

        return new EventoDTO(
                salvo.getTitulo(),
                salvo.getDescricao(),
                salvo.getDataHora(),
                salvo.getLocal());
    }

    @Override
    public List<EventoDTO> listar() {
        return repository.findAllByOrderByDataHoraAsc().stream()
                .map(e -> new EventoDTO(
                        e.getTitulo(),
                        e.getDescricao(),
                        e.getDataHora(),
                        e.getLocal()))
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO atualizar(Long id, EventoDTO dto) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setDataHora(dto.getDataHora());
        evento.setLocal(dto.getLocal());

        Evento atualizado = repository.save(evento);

        return new EventoDTO(
                atualizado.getTitulo(),
                atualizado.getDescricao(),
                atualizado.getDataHora(),
                atualizado.getLocal());
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Evento não encontrado");
        }
        repository.deleteById(id);
    }
}
