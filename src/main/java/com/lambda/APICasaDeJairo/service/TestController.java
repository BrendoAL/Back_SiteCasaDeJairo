package com.lambda.APICasaDeJairo.service;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Teste", description = "Endpoints de teste para verificar funcionamento da API")
public class TestController {

    @GetMapping("/health")
    @Operation(
            summary = "Verificar saúde da API",
            description = "Endpoint público para verificar se a API está funcionando corretamente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "API funcionando corretamente"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "OK");
        response.put("timestamp", LocalDateTime.now());
        response.put("message", "Casa de Jairo API está funcionando!");
        response.put("version", "1.0.0");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/public/{message}")
    @Operation(
            summary = "Endpoint público de teste",
            description = "Endpoint público que retorna uma mensagem personalizada"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Mensagem retornada com sucesso")
    })
    public ResponseEntity<Map<String, String>> publicEndpoint(
            @Parameter(description = "Mensagem a ser retornada", required = true)
            @PathVariable String message) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Você disse: " + message);
        response.put("timestamp", LocalDateTime.now().toString());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/protected")
    @Operation(
            summary = "Endpoint protegido de teste",
            description = "Endpoint que requer autenticação JWT",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso autorizado"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    public ResponseEntity<Map<String, String>> protectedEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Você acessou um endpoint protegido!");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("authenticated", "true");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    @Operation(
            summary = "Endpoint apenas para administradores",
            description = "Endpoint que requer papel de ADMIN",
            security = @SecurityRequirement(name = "Bearer Authentication")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Acesso de admin autorizado"),
            @ApiResponse(responseCode = "401", description = "Token JWT inválido ou ausente"),
            @ApiResponse(responseCode = "403", description = "Usuário não possui papel de ADMIN")
    })
    public ResponseEntity<Map<String, String>> adminEndpoint(
            @Parameter(description = "Dados de teste para administrador")
            @RequestBody(required = false) Map<String, Object> data) {

        Map<String, String> response = new HashMap<>();
        response.put("message", "Você acessou um endpoint de administrador!");
        response.put("timestamp", LocalDateTime.now().toString());
        response.put("role", "ADMIN");
        response.put("data", data != null ? data.toString() : "Nenhum dado enviado");
        return ResponseEntity.ok(response);
    }
}
