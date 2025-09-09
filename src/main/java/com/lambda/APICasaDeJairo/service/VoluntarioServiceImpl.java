package com.lambda.APICasaDeJairo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import org.springframework.stereotype.Service;

//regras de negocio de voluntário
@Service
public class VoluntarioServiceImpl implements VoluntarioService {

    @Autowired
    private VoluntarioRepository repository;

    @Autowired
    private NewsletterService newsletterService;

    @Override
    public VoluntarioDTO criarVoluntario(VoluntarioDTO dto) {
        // Verificar se já existe um voluntário com este email
        if (repository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Já existe um voluntário cadastrado com este email: " + dto.getEmail());
        }

        Voluntario v = new Voluntario();
        v.setNome(dto.getNome());
        v.setEmail(dto.getEmail());
        v.setTelefone(dto.getTelefone());
        v.setDisponibilidade(dto.getDisponibilidade());
        v.setMensagem(dto.getMensagem());
        v.setAceitaEmails(dto.getAceitaEmails() != null ? dto.getAceitaEmails() : false);

        Voluntario salvo = repository.save(v);

        VoluntarioDTO voluntarioSalvo = new VoluntarioDTO(
                salvo.getNome(),
                salvo.getEmail(),
                salvo.getTelefone(),
                salvo.getDisponibilidade(),
                salvo.getMensagem(),
                salvo.getAceitaEmails()
        );

        // Enviar email de boas-vindas se aceitou receber emails
        if (salvo.getAceitaEmails()) {
            try {
                newsletterService.enviarEmailBoasVindas(voluntarioSalvo);
            } catch (Exception e) {
                System.err.println("Erro ao enviar email de boas-vindas: " + e.getMessage());
                // Não falhar o cadastro por causa do email
            }
        }

        return voluntarioSalvo;
    }

    @Override
    public List<VoluntarioDTO> listarVoluntarios() {
        return repository.findAll().stream()
                .map(v -> new VoluntarioDTO(
                        v.getNome(),
                        v.getEmail(),
                        v.getTelefone(),
                        v.getDisponibilidade(),
                        v.getMensagem(),
                        v.getAceitaEmails()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<VoluntarioDTO> listarVoluntariosNewsletter() {
        return repository.findByAceitaEmailsTrue().stream()
                .map(v -> new VoluntarioDTO(
                        v.getNome(),
                        v.getEmail(),
                        v.getTelefone(),
                        v.getDisponibilidade(),
                        v.getMensagem(),
                        v.getAceitaEmails()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public int contarVoluntariosNewsletter() {
        return repository.countByAceitaEmailsTrue();
    }

    @Override
    public VoluntarioDTO buscarPorEmail(String email) {
        Voluntario v = repository.findByEmail(email);
        if (v == null) {
            return null;
        }
        return new VoluntarioDTO(
                v.getNome(),
                v.getEmail(),
                v.getTelefone(),
                v.getDisponibilidade(),
                v.getMensagem(),
                v.getAceitaEmails()
        );
    }
}