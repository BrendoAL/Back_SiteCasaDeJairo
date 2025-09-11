package com.lambda.APICasaDeJairo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByDataAsc();
}
