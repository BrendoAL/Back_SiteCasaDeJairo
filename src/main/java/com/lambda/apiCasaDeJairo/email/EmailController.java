package com.lambda.apiCasaDeJairo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/email")
class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/enviar")
    String enviarEmailComTemplate(@RequestBody EmailRequest request) {
        Map<String, Object> variaveis = new HashMap<>();
        variaveis.put("titulo", request.getTitulo());
        variaveis.put("mensagem", request.getMensagem());

        emailService.enviarEmailSimples(request.getPara(), request.getAssunto(), variaveis);
        return "E-mail com template enviado para " + request.getPara();
    }
}


