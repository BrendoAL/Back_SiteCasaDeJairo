package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/notificar")
    public ResponseEntity<String> notificarEvento(@RequestParam String para) {
        String assunto = "Novo Evento da Casa de Jairo!";
        String corpo = """
                <!DOCTYPE html>
                <html lang="pt-BR">
                <head>
                  <meta charset="UTF-8" />
                  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                  <title>Casa de Jairo - Novo Evento</title>
                  <style>
                    body, table, td, a {
                      -webkit-text-size-adjust: 100%;
                      -ms-text-size-adjust: 100%;
                    }
                    table, td {
                      mso-table-rspace: 0pt;
                      mso-table-lspace: 0pt;
                    }
                    img {
                      -ms-interpolation-mode: bicubic;
                    }
                    body {
                      margin: 0;
                      padding: 0;
                      background-color: #f4f4f4;
                      font-family: Arial, sans-serif;
                      color: #333333;
                    }
                    @media screen and (max-width: 600px) {
                      .wrapper {
                        width: 100% !important;
                      }
                      .responsive-table {
                        width: 100% !important;
                      }
                      .padding {
                        padding: 10px 5% !important;
                      }
                    }
                  </style>
                </head>
                <body>
                  <center>
                    <table border="0" cellpadding="0" cellspacing="0" width="100%" bgcolor="#f4f4f4">
                      <tr>
                        <td align="center" style="padding: 20px 0;">
                          <table class="wrapper" border="0" cellpadding="0" cellspacing="0" width="600" bgcolor="#ffffff" style="border-radius: 8px; overflow: hidden;">
                            <tr>
                              <td align="center" style="background-color: #27ae60; padding: 40px;">
                                <h1 style="color: white; margin: 0;">🎉 Novo Evento Casa de Jairo</h1>
                              </td>
                            </tr>
                            <tr>
                              <td class="padding" style="padding: 30px 40px;">
                                <p style="font-size: 18px; margin: 0 0 15px;">
                                  Olá! Temos um novo evento esperando por você:
                                </p>
                                <h2 style="color: #2c3e50; margin-top: 0;">Título do Evento</h2>
                                <p style="margin: 10px 0;">
                                  Aqui você coloca uma descrição breve do evento, como local, data, e tudo mais que quiser destacar.
                                </p>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0" style="margin: 25px 0;">
                                  <tr>
                                    <td align="center">
                                      <a href="https://seusite.com/eventos" target="_blank" style="background-color: #27ae60; color: white; padding: 15px 30px; text-decoration: none; border-radius: 5px; font-weight: bold; display: inline-block;">
                                        Ver Detalhes do Evento
                                      </a>
                                    </td>
                                  </tr>
                                </table>
                                <p style="font-size: 14px; color: #888;">
                                  Obrigado por fazer parte da nossa comunidade!<br/>
                                  Casa de Jairo © 2025
                                </p>
                              </td>
                            </tr>
                            <tr>
                              <td align="center" style="background-color: #e1e1e1; padding: 15px; font-size: 12px; color: #555;">
                                Você está recebendo este e-mail porque se inscreveu para receber novidades.
                              </td>
                            </tr>
                          </table>
                        </td>
                      </tr>
                    </table>
                  </center>
                </body>
                </html>
                """;


        emailService.enviarEmailSimples(para, assunto, corpo);
        return ResponseEntity.ok("E-mail enviado com sucesso!");
    }

}

