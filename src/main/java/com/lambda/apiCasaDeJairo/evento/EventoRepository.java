package com.lambda.apiCasaDeJairo.evento;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByDataAsc();
}
