package com.lambda.APICasaDeJairo.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.Voluntario;
import org.springframework.stereotype.Repository;

import java.util.List;

//cria as entidades no banco de dados
@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    // Encontrar todos os voluntários que aceitaram receber emails
    List<Voluntario> findByAceitaEmailsTrue();

    // Contar quantos voluntários aceitaram receber emails
    int countByAceitaEmailsTrue();

    // Encontrar voluntário por email
    Voluntario findByEmail(String email);

    // Verificar se já existe um voluntário com o email
    boolean existsByEmail(String email);
}
