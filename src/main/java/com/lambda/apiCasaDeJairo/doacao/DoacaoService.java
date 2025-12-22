package com.lambda.apiCasaDeJairo.doacao;

import java.util.List;

//interface do service
public interface DoacaoService {
    DoacaoDTO criarDoacao(DoacaoDTO dto);
    List<DoacaoDTO> listarDoacao();
    DoacaoDTO buscarPorId(Long id);
    void deletarDoacao(Long id);
}