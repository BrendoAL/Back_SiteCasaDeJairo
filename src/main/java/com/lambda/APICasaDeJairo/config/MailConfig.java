package com.lambda.APICasaDeJairo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig {

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("mail.casadejairo.online"); // Host da HostGator
        mailSender.setPort(587); // STARTTLS
        mailSender.setUsername("contato@casadejairo.online"); // seu email real
        mailSender.setPassword("SUA_SENHA_EMAIL_AQUI"); // senha real

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true"); // debug para ver logs de envio

        return mailSender;
    }
}

