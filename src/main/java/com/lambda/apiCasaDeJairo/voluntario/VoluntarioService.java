package com.lambda.apiCasaDeJairo.voluntario;

import java.util.List;

//interface do service


public interface VoluntarioService {
    VoluntarioDTO criarVoluntario(VoluntarioDTO dto);
    List<VoluntarioDTO> listarVoluntarios();
    void deletarVoluntario(Long id);
    List<VoluntarioDTO> listarVoluntariosNewsletter();
    int contarVoluntariosNewsletter();
    VoluntarioDTO buscarPorEmail(String email);
}
