package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.TransparenciaDTO;
import com.lambda.APICasaDeJairo.models.PostImagem;
import com.lambda.APICasaDeJairo.models.Transparencia;
import com.lambda.APICasaDeJairo.repository.PostImagemRepository;
import com.lambda.APICasaDeJairo.repository.TransparenciaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransparenciaServiceImpl implements TransparenciaService {

    private final TransparenciaRepository repository;

    public TransparenciaServiceImpl(TransparenciaRepository repository) {
        this.repository = repository;
    }

    @Override
    public Transparencia criar(TransparenciaDTO dto) {
        Transparencia t = new Transparencia();
        t.setTitulo(dto.getTitulo());
        t.setDescricao(dto.getDescricao());
        t.setData(dto.getDataPublicacao());

        // Se vier imagem, cria PostImagem e associa
        if (dto.getPostImagemId() != null) {
            PostImagem postImagem = new PostImagem();
            postImagem.setId(dto.getPostImagemId()); // só se você tiver referência
            t.setPostImagem(postImagem);
        }

        return repository.save(t);
    }

    @Override
    public List<TransparenciaDTO> listarTodos() {
        return repository.findAll().stream()
                .map(t -> new TransparenciaDTO(
                        t.getId(),
                        t.getTitulo(),
                        t.getDescricao(),
                        t.getPostImagem() != null ? t.getPostImagem().getId() : null,
                        t.getData()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public byte[] getImagemDoRegistro(Long id) {
        Transparencia t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));
        if (t.getPostImagem() == null) {
            throw new RuntimeException("Imagem não encontrada para este registro");
        }
        return t.getPostImagem().getImagem();
    }

    @Override
    public Transparencia criarComImagem(String titulo, String descricao, String data, MultipartFile imagem) throws IOException {
        Transparencia t = new Transparencia();
        t.setTitulo(titulo);
        t.setDescricao(descricao);
        t.setData(LocalDate.parse(data));

        if (imagem != null && !imagem.isEmpty()) {
            PostImagem postImagem = new PostImagem();
            postImagem.setTitulo("Imagem Transparência");
            postImagem.setConteudo(descricao);
            postImagem.setImagem(imagem.getBytes());

            // associa a imagem ao Transparencia
            t.setPostImagem(postImagem);
        }

        return repository.save(t);
    }

    @Override
    public Transparencia atualizarComImagem(Long id, String titulo, String descricao, String data, MultipartFile imagem) {
        Transparencia t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transparência não encontrada"));

        t.setTitulo(titulo);
        t.setDescricao(descricao);

        if (data != null && !data.trim().isEmpty()) {
            try {
                t.setData(LocalDate.parse(data));
            } catch (DateTimeParseException e) {
                // ignora ou loga
            }
        }

        if (imagem != null && !imagem.isEmpty()) {
            try {
                // Substitui a imagem antiga, se existir
                if (t.getPostImagem() != null) {
                    t.setPostImagem(null); // remove referência anterior
                }

                PostImagem novaImagem = new PostImagem();
                novaImagem.setTitulo(imagem.getOriginalFilename());
                novaImagem.setConteudo(imagem.getContentType());
                novaImagem.setImagem(imagem.getBytes());

                t.setPostImagem(novaImagem);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar imagem", e);
            }
        }

        return repository.save(t);
    }

    @Override
    public Transparencia atualizar(Long id, TransparenciaDTO dto) {
        return repository.findById(id).map(t -> {
            t.setTitulo(dto.getTitulo());
            t.setDescricao(dto.getDescricao());
            t.setData(dto.getDataPublicacao());

            if (dto.getPostImagemId() != null) {
                PostImagem img = new PostImagem();
                img.setId(dto.getPostImagemId());
                t.setPostImagem(img);
            }

            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Registro não encontrado"));
    }

    @Override
    public void deletar(Long id) {
        Transparencia t = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));
        repository.delete(t);
    }

    @Override
    public byte[] getImagemPorId(Long imagemId) {
        return repository.findAll().stream()
                .filter(t -> t.getPostImagem() != null && t.getPostImagem().getId().equals(imagemId))
                .map(t -> t.getPostImagem().getImagem())
                .findFirst()
                .orElse(new byte[0]);
    }
}
