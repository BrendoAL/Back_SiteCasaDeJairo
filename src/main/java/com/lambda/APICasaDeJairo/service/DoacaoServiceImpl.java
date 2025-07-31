package com.lambda.APICasaDeJairo.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import com.lambda.APICasaDeJairo.exceptions.DadosInvalidosException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Doacao;
import com.lambda.APICasaDeJairo.repository.DoacaoRepository;

//regras de negocio da doacao
@Service
public class DoacaoServiceImpl implements DoacaoService {

    @Autowired
    private DoacaoRepository repository;

    @Override
    public DoacaoDTO criarDoacao(DoacaoDTO dto) {
        Doacao doacao = new Doacao();

        if (dto.getValor() == null || dto.getValor().compareTo(BigDecimal.ZERO) <= 0) {
            throw new DadosInvalidosException("O valor da doação deve ser maior que zero.");
        }

        if (dto.getNomeDoador() == null || dto.getNomeDoador().trim().isEmpty()) {
            throw new DadosInvalidosException("O nome do doador não pode ser nulo ou vazio.");
        }

        if (dto.getMetodoPagamento() == null || dto.getMetodoPagamento().trim().isEmpty()) {
            throw new DadosInvalidosException("O método de pagamento é obrigatório.");
        }
        doacao.setMensagem(dto.getMensagem());

        Doacao salva = repository.save(doacao);

        return new DoacaoDTO(
                salva.getNomeDoador(),
                salva.getValor(),
                salva.getMetodoPagamento(),
                salva.getMensagem());
    }

    @Override
    public List<DoacaoDTO> listarDoacao() {
        return repository.findAll().stream()
                .map(d -> new DoacaoDTO(
                        d.getNomeDoador(),
                        d.getValor(),
                        d.getMetodoPagamento(),
                        d.getMensagem()))
                .collect(Collectors.toList());
    }

    @Override
    public DoacaoDTO buscarPorId(Long id) {
        Doacao doacao = repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Doação não encontrada"));
        return new DoacaoDTO(
                doacao.getNomeDoador(),
                doacao.getValor(),
                doacao.getMetodoPagamento(),
                doacao.getMensagem());
    }

    @Override
    public void deletarDoacao(Long id) {
        if (!repository.existsById(id)) {
            throw new RecursoNaoEncontradoException("Doação não encontrada");
        }
        repository.deleteById(id);
    }
}
