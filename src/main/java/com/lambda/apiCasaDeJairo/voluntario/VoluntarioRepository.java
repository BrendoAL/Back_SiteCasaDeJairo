package com.lambda.apiCasaDeJairo.voluntario;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    List<Voluntario> findByAceitaEmailsTrue();

    int countByAceitaEmailsTrue();

    Voluntario findByEmail(String email);

    boolean existsByEmail(String email);
}
