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
    private final PostImagemRepository postImagemRepository;

    public TransparenciaServiceImpl(TransparenciaRepository repository, PostImagemRepository postImagemRepository) {
        this.repository = repository;
        this.postImagemRepository = postImagemRepository;
    }

    @Override
    public Transparencia criar(TransparenciaDTO dto) {
        Transparencia t = new Transparencia();
        t.setTitulo(dto.getTitulo());
        t.setDescricao(dto.getDescricao());
        t.setData(dto.getDataPublicacao());

        if (dto.getPostImagemId() != null) {
            PostImagem imagem = postImagemRepository.findById(dto.getPostImagemId())
                    .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
            t.setPostImagem(imagem);
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
    public Transparencia atualizar(Long id, TransparenciaDTO dto) {
        return repository.findById(id).map(t -> {
            t.setTitulo(dto.getTitulo());
            t.setDescricao(dto.getDescricao());
            t.setData(dto.getDataPublicacao());

            if (dto.getPostImagemId() != null) {
                PostImagem imagem = postImagemRepository.findById(dto.getPostImagemId())
                        .orElseThrow(() -> new RuntimeException("Imagem não encontrada"));
                t.setPostImagem(imagem);
            }

            return repository.save(t);
        }).orElseThrow(() -> new RuntimeException("Registro não encontrado"));
    }

    @Override
    public void deletar(Long id) {
        Transparencia transparencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Registro não encontrado"));
        repository.delete(transparencia); // JPA já remove a imagem junto
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
            postImagem = postImagemRepository.save(postImagem);

            t.setPostImagem(postImagem);
        }

        return repository.save(t);
    }

    @Override
    public Transparencia atualizarComImagem(Long id, String titulo, String descricao, String data, MultipartFile imagem) {
        Transparencia transparencia = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Transparência não encontrada"));

        transparencia.setTitulo(titulo);
        transparencia.setDescricao(descricao);

        if (data != null && !data.trim().isEmpty()) {
            try {
                transparencia.setData(LocalDate.parse(data));
            } catch (DateTimeParseException e) {
                // Ignora ou loga erro de data inválida
            }
        }

        // Substitui a imagem (se enviada)
        if (imagem != null && !imagem.isEmpty()) {
            try {
                // Remove imagem antiga, se existir
                PostImagem imagemAntiga = transparencia.getPostImagem();
                if (imagemAntiga != null) {
                    postImagemRepository.delete(imagemAntiga);
                }

                // Cria e salva nova imagem
                PostImagem novaImagem = new PostImagem();
                novaImagem.setTitulo(imagem.getOriginalFilename());
                novaImagem.setConteudo(imagem.getContentType());
                novaImagem.setImagem(imagem.getBytes());

                PostImagem imagemSalva = postImagemRepository.save(novaImagem);
                transparencia.setPostImagem(imagemSalva);

            } catch (IOException e) {
                throw new RuntimeException("Erro ao processar imagem", e);
            }
        }
        return repository.save(transparencia);
    }

    @Override
    public byte[] getImagemPorId(Long imagemId) {
        return postImagemRepository.findById(imagemId)
                .map(PostImagem::getImagem)
                .orElse(new byte[0]);
    }
}