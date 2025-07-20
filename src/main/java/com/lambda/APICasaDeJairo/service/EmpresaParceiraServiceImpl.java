package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EmpresaPerceiraDTO;

import com.lambda.APICasaDeJairo.models.EmpresaParceira;
import com.lambda.APICasaDeJairo.repository.EmpresaParceiraRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaParceiraServiceImpl implements EmpresaParceiraService {


    @Autowired
    private EmpresaParceiraRepository repository;

    @Override
    public EmpresaPerceiraDTO criarEmpresaParceira(EmpresaPerceiraDTO dto) {
        EmpresaParceira v = new EmpresaParceira();
        v.setNome(dto.getNome());
        v.setEmail(dto.getEmail());
        v.setTelefone(dto.getTelefone());

        EmpresaParceira salvo = repository.save(v);

        return new EmpresaPerceiraDTO(salvo.getNome(), salvo.getEmail(), salvo.getTelefone());
    }

    @Override
    public List<EmpresaPerceiraDTO> listarEmpresaParceira() {
        return repository.findAll().stream()
                .map(v -> new EmpresaPerceiraDTO(v.getNome(), v.getEmail(), v.getTelefone()))
                .collect(Collectors.toList());
    }
}
