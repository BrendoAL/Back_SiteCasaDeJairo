package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;

//interface do service
public interface DoacaoService {
    DoacaoDTO criarDoacao(DoacaoDTO dto);
    List<DoacaoDTO> listarDoacao();
}