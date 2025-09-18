package com.lambda.APICasaDeJairo.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Evento;
import com.lambda.APICasaDeJairo.repository.EventoRepository;
import org.springframework.web.multipart.MultipartFile;

@Service
public class EventoServiceImpl implements EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private VoluntarioRepository voluntarioRepository;

    @Override
    public EventoDTO criar(EventoDTO dto, MultipartFile imagem) throws IOException {
        System.out.println("=== CRIANDO EVENTO ===");
        System.out.println("Dados: " + dto.getTitulo() + " - " + dto.getData());
        System.out.println("Imagem: " + (imagem != null ? imagem.getOriginalFilename() + " (" + imagem.getSize() + " bytes)" : "null"));

        Evento evento = new Evento();
        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        Evento salvo = repository.save(evento);
        System.out.println("Evento salvo com ID: " + salvo.getId());

        if (imagem != null && !imagem.isEmpty()) {
            try {
                if (imagem.getSize() > 10 * 1024 * 1024) { // 10MB
                    throw new IllegalArgumentException("Imagem muito grande. Máximo 10MB.");
                }

                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Arquivo deve ser uma imagem.");
                }

                // Caminho da pasta onde as imagens serão salvas
                Path pasta = Paths.get("uploads/eventos/");
                if (!Files.exists(pasta)) {
                    Files.createDirectories(pasta);
                    System.out.println("Pasta criada: " + pasta.toAbsolutePath());
                }

                String nomeOriginal = imagem.getOriginalFilename();
                String extensao = ".jpg"; // Default
                if (nomeOriginal != null && nomeOriginal.contains(".")) {
                    extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                }

                Path caminhoArquivo = pasta.resolve(salvo.getId() + extensao);
                Files.write(caminhoArquivo, imagem.getBytes());
                System.out.println("Imagem salva em: " + caminhoArquivo.toAbsolutePath());

                salvo.setImagemUrl("/api/eventos/imagem/" + salvo.getId());
                repository.save(salvo); // atualiza no banco
                System.out.println("URL da imagem atualizada: " + salvo.getImagemUrl());

            } catch (IOException e) {
                System.err.println("Erro ao salvar imagem: " + e.getMessage());
                e.printStackTrace();
                throw new IOException("Erro ao salvar imagem: " + e.getMessage());
            }
        }

        try {
            notificarUsuariosSobreEvento(salvo.getId()); // envia o ID do evento
        } catch (Exception e) {
            System.err.println("Erro ao enviar emails de notificação: " + e.getMessage());
        }

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
                .map(e -> {
                    String imagemUrl = e.getImagemUrl();
                    if (imagemUrl == null && e.getId() != null) {
                        // Verificar se existe arquivo de imagem
                        Path caminhoImagem = Paths.get("uploads/eventos/" + e.getId() + ".jpg");
                        if (Files.exists(caminhoImagem)) {
                            imagemUrl = "/api/eventos/imagem/" + e.getId();
                            // Atualiza no banco
                            e.setImagemUrl(imagemUrl);
                            repository.save(e);
                        }
                    }

                    return new EventoDTO(
                            e.getId(),
                            e.getTitulo(),
                            e.getDescricao(),
                            e.getData(),
                            e.getLocal(),
                            imagemUrl
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public EventoDTO atualizar(Long id, EventoDTO dto, MultipartFile imagem) throws IOException {
        System.out.println("=== ATUALIZANDO EVENTO ID: " + id + " ===");
        System.out.println("Nova imagem: " + (imagem != null ? imagem.getOriginalFilename() : "null"));

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado"));

        evento.setTitulo(dto.getTitulo());
        evento.setDescricao(dto.getDescricao());
        evento.setData(dto.getData());
        evento.setLocal(dto.getLocal());

        if (imagem != null && !imagem.isEmpty()) {
            try {
                // Validações de imagem
                if (imagem.getSize() > 20 * 1024 * 1024) { // 20MB
                    throw new IllegalArgumentException("Imagem muito grande. Máximo 20MB.");
                }

                String contentType = imagem.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    throw new IllegalArgumentException("Arquivo deve ser uma imagem.");
                }

                // Caminho da pasta onde as imagens serão salvas
                Path pasta = Paths.get("uploads/eventos/");
                if (!Files.exists(pasta)) {
                    Files.createDirectories(pasta);
                }

                // Remove imagem antiga se existir
                String[] extensoesPossiveis = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
                for (String ext : extensoesPossiveis) {
                    Path imagemAntiga = pasta.resolve(evento.getId() + ext);
                    if (Files.exists(imagemAntiga)) {
                        Files.delete(imagemAntiga);
                        System.out.println("Imagem antiga removida: " + imagemAntiga);
                        break;
                    }
                }

                // Salva nova imagem
                String nomeOriginal = imagem.getOriginalFilename();
                String extensao = ".jpg"; // Default
                if (nomeOriginal != null && nomeOriginal.contains(".")) {
                    extensao = nomeOriginal.substring(nomeOriginal.lastIndexOf("."));
                }

                Path caminhoArquivo = pasta.resolve(evento.getId() + extensao);
                Files.write(caminhoArquivo, imagem.getBytes());
                System.out.println("Nova imagem salva: " + caminhoArquivo);

                // Atualiza a URL da imagem no evento
                evento.setImagemUrl("/api/eventos/imagem/" + evento.getId());
            } catch (IOException e) {
                System.err.println("Erro ao atualizar imagem: " + e.getMessage());
                throw new IOException("Erro ao processar imagem: " + e.getMessage());
            }
        }

        Evento atualizado = repository.save(evento);
        System.out.println("Evento atualizado com sucesso");

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

        // Remove arquivo de imagem ao deletar evento
        try {
            String[] extensoesPossiveis = {".jpg", ".jpeg", ".png", ".gif", ".webp"};
            Path pasta = Paths.get("uploads/eventos/");

            for (String ext : extensoesPossiveis) {
                Path imagemArquivo = pasta.resolve(id + ext);
                if (Files.exists(imagemArquivo)) {
                    Files.delete(imagemArquivo);
                    System.out.println("Imagem do evento deletada: " + imagemArquivo);
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao deletar imagem do evento: " + e.getMessage());
            // Não falha a exclusão do evento se não conseguir deletar a imagem
        }

        repository.deleteById(id);
        System.out.println("Evento ID " + id + " deletado com sucesso");
    }

    @Override
    public void notificarUsuariosSobreEvento(Long eventoId) {
        try {
            Evento evento = repository.findById(eventoId)
                    .orElseThrow(() -> new RuntimeException("Evento não encontrado"));

            List<Voluntario> voluntarios = voluntarioRepository.findByAceitaEmailsTrue();
            for (Voluntario v : voluntarios) {
                Map<String, Object> variaveis = new HashMap<>();
                variaveis.put("titulo", evento.getTitulo());
                variaveis.put("mensagem", evento.getDescricao());
                variaveis.put("nomeVoluntario", v.getNome());
                variaveis.put("data", evento.getData().toString());
                variaveis.put("local", evento.getLocal());

                emailService.enviarEmailComTemplate(
                        v.getEmail(),
                        "Novo evento: " + evento.getTitulo(),
                        variaveis,
                        "email-evento-template" // Thymeleaf template
                );
            }
        } catch (Exception e) {
            System.err.println("Erro ao enviar notificações: " + e.getMessage());
        }
    }


    public byte[] getImagemById(Long id) throws IOException {
        System.out.println("Buscando imagem para evento ID: " + id);

        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com id: " + id));

        Path pasta = Paths.get("uploads/eventos/");

        String[] extensoesPossiveis = {".jpg", ".jpeg", ".png", ".gif", ".webp"};

        for (String extensao : extensoesPossiveis) {
            Path caminhoArquivo = pasta.resolve(id + extensao);
            System.out.println("Tentando: " + caminhoArquivo.toAbsolutePath());

            if (Files.exists(caminhoArquivo)) {
                System.out.println("Imagem encontrada: " + caminhoArquivo);
                return Files.readAllBytes(caminhoArquivo);
            }
        }

        System.out.println("Nenhuma imagem encontrada para evento ID: " + id);
        throw new RuntimeException("Imagem não encontrada para evento ID: " + id);
    }

    @Override
    public EventoDTO buscarPorId(Long id) {
        Evento evento = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Evento não encontrado com id: " + id));

        return new EventoDTO(
                evento.getId(),
                evento.getTitulo(),
                evento.getDescricao(),
                evento.getData(),
                evento.getLocal(),
                evento.getImagemUrl()
        );
    }
}