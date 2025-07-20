package com.lambda.APICasaDeJairo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Evento;

/**
 * Interface de repositório para a entidade Evento.
 * Estende JpaRepository para fornecer operações CRUD e consultas no banco de dados.
 * Inclui um método personalizado para listar eventos ordenados por data e hora em ordem crescente.
 */

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findAllByOrderByDataHoraAsc();
}
