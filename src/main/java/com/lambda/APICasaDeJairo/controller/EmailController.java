package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.EmailRequest;
import com.lambda.APICasaDeJairo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    public String enviarEmailComTemplate(@RequestBody EmailRequest request) {
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("titulo", request.getTitulo());
        variaveis.put("mensagem", request.getMensagem());

        emailService.enviarEmailComTemplate(request.getPara(), request.getAssunto(), variaveis);
        return "E-mail com template enviado para " + request.getPara();
    }
}

