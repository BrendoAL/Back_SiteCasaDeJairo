package com.lambda.APICasaDeJairo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.APICasaDeJairo.dto.EmpresaParceiraDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.EmpresaParceira;
import com.lambda.APICasaDeJairo.repository.EmpresaParceiraRepository;

//regras de negocio da empresaParceira
@Service
public class EmpresaParceiraServiceImpl implements EmpresaParceiraService {

    @Autowired
    private EmpresaParceiraRepository repository;

    @Override
    public EmpresaParceiraDTO criarEmpresaParceira(EmpresaParceiraDTO dto) {
        EmpresaParceira v = new EmpresaParceira();
        v.setNome(dto.getNome());
        v.setEmail(dto.getEmail());
        v.setTelefone(dto.getTelefone());

        EmpresaParceira salvo = repository.save(v);

        return new EmpresaParceiraDTO(salvo.getNome(), salvo.getEmail(), salvo.getTelefone());
    }

    @Override
    public EmpresaParceiraDTO buscarPorId(Long id) {
        EmpresaParceira empresa = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa parceira não encontrada"));
        return new EmpresaParceiraDTO(empresa.getNome(), empresa.getEmail(), empresa.getTelefone());
    }

    @Override
    public List<EmpresaParceiraDTO> listarEmpresaParceira() {
        return repository.findAll().stream()
                .map(v -> new EmpresaParceiraDTO(v.getNome(), v.getEmail(), v.getTelefone()))
                .collect(Collectors.toList());
    }

    @Override
    public EmpresaParceiraDTO atualizar(Long id, EmpresaParceiraDTO dto) {
        EmpresaParceira empresa = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Empresa parceira não encontrada"));

        empresa.setNome(dto.getNome());
        empresa.setEmail(dto.getEmail());
        empresa.setTelefone(dto.getTelefone());

        EmpresaParceira atualizada = repository.save(empresa);
        return new EmpresaParceiraDTO(atualizada.getNome(), atualizada.getEmail(), atualizada.getTelefone());
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Empresa parceira não encontrada");
        }
        repository.deleteById(id);
    }
}
