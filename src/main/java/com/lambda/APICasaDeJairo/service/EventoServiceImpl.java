package com.lambda.APICasaDeJairo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Evento;
import com.lambda.APICasaDeJairo.repository.EventoRepository;
import org.springframework.web.multipart.MultipartFile;

/**
 * Implementação da interface EventoService que gerencia a lógica de negócio dos
 * eventos.
 * Responsável por criar, listar, atualizar e deletar eventos no banco de dados
 * usando o EventoRepository. Faz a conversão entre a entidade Evento e o
 * EventoDTO.
 */

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Autowired
    private PostImagemService postImagemService; // serviço para salvar imagens

    @Override
    public EventoDTO criar(EventoDTO dto, MultipartFile imagem) throws IOException {
        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        // Salva o evento primeiro para ter o ID
        Evento salvo = repository.save(evento);

        if (imagem != null && !imagem.isEmpty()) {
            // Caminho da pasta onde as imagens serão salvas
            Path pasta = Paths.get("uploads/eventos/");
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }

            // Nome do arquivo será o ID do evento
            Path caminhoArquivo = pasta.resolve(salvo.getId() + ".jpg");
            Files.write(caminhoArquivo, imagem.getBytes());

            // Atualiza a URL da imagem no evento
            salvo.setImagemUrl("/api/eventos/imagem/" + salvo.getId());
            repository.save(salvo); // atualiza no banco
        }

        // Envia e-mail para os voluntários inscritos
        notificarUsuariosSobreEvento(salvo);

        return new EventoDTO(
                salvo.getId(),
                salvo.getTitulo(),
                salvo.getDescricao(),
                salvo.getData(),
                salvo.getLocal(),
                salvo.getImagemUrl()
        );
    }

    @Override
    public List<EventoDTO> listar() {
        return repository.findAllByOrderByDataAsc().stream()
                .map(e -> new EventoDTO(
                        e.getId(),
                        e.getTitulo(),
                        e.getDescricao(),
                        e.getData(),
                        e.getLocal(),
                        e.getImagemUrl()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO atualizar(Long id, EventoDTO dto, MultipartFile imagem) throws IOException {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        if (imagem != null && !imagem.isEmpty()) {
            // Caminho da pasta onde as imagens serão salvas
            Path pasta = Paths.get("uploads/eventos/");
            if (!Files.exists(pasta)) {
                Files.createDirectories(pasta);
            }

            // Nome do arquivo será o ID do evento (substitui a antiga)
            Path caminhoArquivo = pasta.resolve(evento.getId() + ".jpg");
            Files.write(caminhoArquivo, imagem.getBytes());

            // Atualiza a URL da imagem no evento
            evento.setImagemUrl("/api/eventos/imagem/" + evento.getId());
        }

        Evento atualizado = repository.save(evento);

        return new EventoDTO(
                atualizado.getId(),
                atualizado.getTitulo(),
                atualizado.getDescricao(),
                atualizado.getData(),
                atualizado.getLocal(),
                atualizado.getImagemUrl()
        );
    }

    @Override
    public void deletar(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Evento não encontrado");
        }
        repository.deleteById(id);
    }

    private void notificarUsuariosSobreEvento(Evento evento) {
        List<Voluntario> voluntarios = voluntarioRepository.findByReceberNewsletterTrue();

        for (Voluntario voluntario : voluntarios) {
            String assunto = "Novo Evento da Casa de Jairo!";

            Map<String, Object> variaveis = new HashMap<>();
            variaveis.put("titulo", "Novo Evento da Casa de Jairo!");
            variaveis.put("mensagem",
                    "Olá, " + voluntario.getNome() + "!<br>" +
                            "Foi publicado um novo evento:<br><br>" +
                            "<strong>" + evento.getTitulo() + "</strong><br>" +
                            evento.getDescricao() + "<br><br>" +
                            "<strong>Data:</strong> " + evento.getData() + "<br>" +
                            "<strong>Local:</strong> " + evento.getLocal() + "<br><br>" +
                            "Equipe Casa de Jairo ❤️"
            );

            emailService.enviarEmailSimples(voluntario.getEmail(), assunto, variaveis);
        }
    }

    public byte[] getImagemById(Long id) throws IOException {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com id: " + id));

        // Supondo que você salvou a imagem no disco
        if (evento.getImagemUrl() == null || evento.getImagemUrl().isEmpty()) {
            throw new RuntimeException("Evento não possui imagem");
        }

        // Caminho do arquivo no disco (ajuste conforme sua pasta)
        String caminhoArquivo = "uploads/eventos/" + id + ".jpg";
        Path path = Paths.get(caminhoArquivo);

        if (!Files.exists(path)) {
            throw new RuntimeException("Arquivo de imagem não encontrado");
        }

        return Files.readAllBytes(path);
    }

}

