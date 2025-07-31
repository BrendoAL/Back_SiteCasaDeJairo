package com.lambda.APICasaDeJairo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Voluntario;

import java.util.List;

//cria as entidades no banco de dados
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {
    List<Voluntario> findByReceberNewsletterTrue();
}
