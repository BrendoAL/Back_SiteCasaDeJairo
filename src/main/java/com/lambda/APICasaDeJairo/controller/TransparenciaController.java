package com.lambda.APICasaDeJairo.controller;

import com.lambda.APICasaDeJairo.dto.TransparenciaDTO;
import com.lambda.APICasaDeJairo.models.Transparencia;
import com.lambda.APICasaDeJairo.service.TransparenciaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/transparencia")
@CrossOrigin(origins = "http://localhost:4200")
public class TransparenciaController {

    private final TransparenciaService service;

    public TransparenciaController(TransparenciaService service) {
        this.service = service;
    }

    // GET público: lista todas as transparências
    @GetMapping
    public List<TransparenciaDTO> listar() {
        return service.listarTodos();
    }

    // POST → admin: cria um registro de transparência associado a uma imagem (JSON)
    @PostMapping
    public ResponseEntity<Transparencia> criar(@RequestBody TransparenciaDTO dto) {
        Transparencia t = service.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(t);
    }

    // POST com upload de imagem junto
    @PostMapping(value = "/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Transparencia> criarComImagem(
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        try {
            Transparencia t = service.criarComImagem(titulo, descricao, data, imagem);
            return ResponseEntity.status(HttpStatus.CREATED).body(t);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔹 NOVO: PUT com upload de imagem
    @PutMapping(value = "/{id}/com-imagem", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Transparencia> atualizarComImagem(
            @PathVariable Long id,
            @RequestParam String titulo,
            @RequestParam String descricao,
            @RequestParam String data,
            @RequestPart(required = false) MultipartFile imagem
    ) {
        try {
            Transparencia t = service.atualizarComImagem(id, titulo, descricao, data, imagem);
            return ResponseEntity.ok(t);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // 🔹 CORRIGIDO: GET imagem por postImagemId (não pelo ID do post)
    @GetMapping("/imagem/{postImagemId}")
    public ResponseEntity<byte[]> getImagem(@PathVariable Long postImagemId) {
        byte[] img = service.getImagemPorId(postImagemId);
        if (img == null || img.length == 0) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // ou determine o tipo dinamicamente
        return ResponseEntity.ok().headers(headers).body(img);
    }

    // PUT → admin: atualiza registro existente (sem imagem)
    @PutMapping("/{id}")
    public ResponseEntity<Transparencia> atualizar(@PathVariable Long id, @RequestBody TransparenciaDTO dto) {
        Transparencia t = service.atualizar(id, dto);
        return ResponseEntity.ok(t);
    }

    // DELETE → admin: remove registro
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}