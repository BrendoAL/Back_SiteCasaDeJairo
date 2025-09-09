package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class NewsletterService {

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private EmailService emailService;

    private String formatarData(LocalDate data) {
        if (data == null) {
            return "Data não informada";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return data.format(formatter);
    }

    public void enviarNotificacaoNovoEvento(EventoDTO evento) {
        // Buscar todos os voluntários que aceitaram receber emails
        List<Voluntario> voluntariosNewsletter = voluntarioRepository.findByAceitaEmailsTrue();

        if (voluntariosNewsletter.isEmpty()) {
            System.out.println("Nenhum voluntário inscrito na newsletter.");
            return;
        }

        // Preparar as variáveis para o template de email
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("tituloEvento", evento.getTitulo());
        variaveis.put("descricaoEvento", evento.getDescricao());
        variaveis.put("dataEvento", formatarData(evento.getData()));
        variaveis.put("localEvento", evento.getLocal());
        variaveis.put("organizacao", "ONG Casa de Jairo");

        String assunto = "Novo Evento: " + evento.getTitulo() + " - Casa de Jairo";

        // Enviar email para cada voluntário
        for (Voluntario voluntario : voluntariosNewsletter) {
            try {
                // Personalizar com o nome do voluntário
                variaveis.put("nomeVoluntario", voluntario.getNome());

                emailService.enviarEmailSimples(voluntario.getEmail(), assunto, variaveis);
                System.out.println("Email enviado para: " + voluntario.getEmail());

            } catch (Exception e) {
                System.err.println("Erro ao enviar email para " + voluntario.getEmail() + ": " + e.getMessage());
            }
        }

        System.out.println("Newsletter enviada para " + voluntariosNewsletter.size() + " voluntários.");
    }

    public void enviarEmailBoasVindas(VoluntarioDTO voluntario) {
        if (!voluntario.getAceitaEmails()) {
            return; // Não enviar se não aceitou receber emails
        }

        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("nomeVoluntario", voluntario.getNome());
        variaveis.put("organizacao", "ONG Casa de Jairo");

        String assunto = "Bem-vindo(a) à Casa de Jairo, " + voluntario.getNome() + "!";

        try {
            emailService.enviarEmailBemVindo(voluntario.getEmail(), assunto, variaveis);
            System.out.println("Email de boas-vindas enviado para: " + voluntario.getEmail());
        } catch (Exception e) {
            System.err.println("Erro ao enviar email de boas-vindas: " + e.getMessage());
        }
    }

    private String formatarData(String data) {
        try {
            LocalDate localDate = LocalDate.parse(data);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return localDate.format(formatter);
        } catch (Exception e) {
            return data; // Retorna a data original se houver erro na formatação
        }
    }

    public int contarVoluntariosNewsletter() {
        return voluntarioRepository.countByAceitaEmailsTrue();
    }
}
