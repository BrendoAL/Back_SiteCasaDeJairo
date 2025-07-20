package com.lambda.APICasaDeJairo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lambda.APICasaDeJairo.models.EmpresaParceira;

//cria as entidades no banco de dados
public interface EmpresaParceiraRepository extends JpaRepository<EmpresaParceira, Long> {
}
