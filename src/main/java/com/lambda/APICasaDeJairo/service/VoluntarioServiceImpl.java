package com.lambda.APICasaDeJairo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;

//regras de negocio da doacao
@Service
public class VoluntarioServiceImpl implements VoluntarioService {

    @Autowired
    private VoluntarioRepository repository;

    @Override
    public VoluntarioDTO criarVoluntario(VoluntarioDTO dto) {
        Voluntario v = new Voluntario();
        v.setNome(dto.getNome());
        v.setEmail(dto.getEmail());
        v.setTelefone(dto.getTelefone());

        Voluntario salvo = repository.save(v);

        return new VoluntarioDTO(salvo.getNome(), salvo.getEmail(), salvo.getTelefone());
    }

    @Override
    public List<VoluntarioDTO> listarVoluntarios() {
        return repository.findAll().stream()
                .map(v -> new VoluntarioDTO(v.getNome(), v.getEmail(), v.getTelefone()))
                .collect(Collectors.toList());
    }
}
