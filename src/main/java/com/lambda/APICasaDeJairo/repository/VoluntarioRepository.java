package com.lambda.APICasaDeJairo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Voluntario;

//cria as entidades no banco de dados
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
}
