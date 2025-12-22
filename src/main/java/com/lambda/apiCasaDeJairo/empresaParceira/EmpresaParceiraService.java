package com.lambda.apiCasaDeJairo.empresaParceira;

import java.util.List;

//interface do service
public interface EmpresaParceiraService {
    EmpresaParceiraDTO criarEmpresaParceira(EmpresaParceiraDTO dto);

    List<EmpresaParceiraDTO> listarEmpresaParceira();

    EmpresaParceiraDTO buscarPorId(Long id);

    EmpresaParceiraDTO atualizar(Long id, EmpresaParceiraDTO dto);

    void deletar(Long id);

}
