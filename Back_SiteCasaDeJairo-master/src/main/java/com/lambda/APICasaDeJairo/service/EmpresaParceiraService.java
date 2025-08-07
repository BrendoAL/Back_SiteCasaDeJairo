package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.EmpresaParceiraDTO;

//interface do service
public interface EmpresaParceiraService {
    EmpresaParceiraDTO criarEmpresaParceira(EmpresaParceiraDTO dto);

    List<EmpresaParceiraDTO> listarEmpresaParceira();

    EmpresaParceiraDTO buscarPorId(Long id);

    EmpresaParceiraDTO atualizar(Long id, EmpresaParceiraDTO dto);

    void deletar(Long id);

}
