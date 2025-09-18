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

    public void enviarEmailSimples(String para, String assunto, Map<String, Object> variaveis) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String html = templateEngine.process("email-evento-template.html", context);

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom("contato.casadejairo@gmail.com");

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }

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

    public void enviarEmailParaLista(String[] destinatarios, String assunto, Map<String, Object> variaveis) {

    }

    public void enviarEmailComTemplate(String email, String assunto, Map<String, Object> variaveis, String templateNome) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            Context context = new Context();
            context.setVariables(variaveis);

            String html = templateEngine.process(templateNome, context);

            helper.setTo(email);
            helper.setSubject(assunto);
            helper.setText(html, true);
            helper.setFrom("contato.casadejairo@gmail.com");

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}