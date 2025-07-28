package com.lambda.APICasaDeJairo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Voluntario;

//JPA
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
}
