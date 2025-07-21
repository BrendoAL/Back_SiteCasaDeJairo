package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EmpresaPerceiraDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
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
    public EmpresaPerceiraDTO buscarPorId(Long id) {
        EmpresaParceira empresa = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa parceira não encontrada"));
        return new EmpresaPerceiraDTO(empresa.getNome(), empresa.getEmail(), empresa.getTelefone());
    }

    @Override
    public List<EmpresaPerceiraDTO> listarEmpresaParceira() {
        return repository.findAll().stream()
                .map(v -> new EmpresaPerceiraDTO(v.getNome(), v.getEmail(), v.getTelefone()))
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaPerceiraDTO atualizar(Long id, EmpresaPerceiraDTO dto) {
        EmpresaParceira empresa = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa parceira não encontrada"));

        empresa.setNome(dto.getNome());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefone(dto.getTelefone());

        EmpresaParceira atualizada = repository.save(empresa);
        return new EmpresaPerceiraDTO(atualizada.getNome(), atualizada.getEmail(), atualizada.getTelefone());
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Empresa parceira não encontrada");
        }
        repository.deleteById(id);
    }
}
