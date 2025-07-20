package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.EmpresaPerceiraDTO;

public interface EmpresaParceiraService {
    EmpresaPerceiraDTO criarEmpresaParceira(EmpresaPerceiraDTO dto);
    List<EmpresaPerceiraDTO> listarEmpresaParceira();
    
}
