package com.lambda.APICasaDeJairo.repository;

import com.lambda.APICasaDeJairo.models.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

//cria as entidades no banco de dados
public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}
