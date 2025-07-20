package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;

//interface do service
public interface VoluntarioService {
    VoluntarioDTO criarVoluntario(VoluntarioDTO dto);
    List<VoluntarioDTO> listarVoluntarios();
}
