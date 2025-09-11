package com.lambda.APICasaDeJairo.service;

import java.util.List;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;

//interface do service
import java.util.List;

public interface VoluntarioService {
    VoluntarioDTO criarVoluntario(VoluntarioDTO dto);
    List<VoluntarioDTO> listarVoluntarios();
    void deletarVoluntario(Long id);
    List<VoluntarioDTO> listarVoluntariosNewsletter();
    int contarVoluntariosNewsletter();
    VoluntarioDTO buscarPorEmail(String email);
}
