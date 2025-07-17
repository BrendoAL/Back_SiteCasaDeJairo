package com.lambda.APICasaDeJairo.repository;

import com.lambda.APICasaDeJairo.models.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByDataHoraAsc();
}
