package com.lambda.APICasaDeJairo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Map;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private final SpringTemplateEngine templateEngine;

    public EmailService(JavaMailSender mailSender, SpringTemplateEngine templateEngine) {
        this.mailSender = mailSender;
        this.templateEngine = templateEngine;
    }

    // Método existente para envio simples
    public void enviarEmailSimples(String para, String assunto, Map<String, Object> variaveis) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String html = templateEngine.process("email-evento-template", context);

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom("contato.casadejairo@gmail.com");

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

    // Novo método para email de boas-vindas
    public void enviarEmailBemVindo(String para, String assunto, Map<String, Object> variaveis) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String html = templateEngine.process("email-bem-vindo-template", context);

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom("contato.casadejairo@gmail.com");

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail de boas-vindas: " + e.getMessage());
        }
    }

    // Método para enviar email para múltiplos destinatários
    public void enviarEmailParaLista(String[] destinatarios, String assunto, Map<String, Object> variaveis) {
        for (String destinatario : destinatarios) {
            try {
                enviarEmailSimples(destinatario, assunto, variaveis);
                Thread.sleep(100); // Pequena pausa para evitar spam
            } catch (Exception e) {
                System.err.println("Erro ao enviar email para " + destinatario + ": " + e.getMessage());
            }
        }
    }
}