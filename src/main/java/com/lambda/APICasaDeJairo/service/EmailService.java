package com.lambda.APICasaDeJairo.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void enviarEmailSimples(String para, String assunto, String corpo) {
        try {
            MimeMessage mensagem = mailSender.createMimeMessage();


            MimeMessageHelper helper = new MimeMessageHelper(mensagem, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(assunto);
            helper.setText(corpo, true);  // true indica que o corpo é HTML
            helper.setFrom("contato.casadejairo@gmail.com");

            mailSender.send(mensagem);
        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao enviar e-mail: " + e.getMessage());
        }
    }
}

