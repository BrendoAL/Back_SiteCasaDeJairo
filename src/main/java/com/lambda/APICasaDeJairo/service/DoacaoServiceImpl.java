package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;
import com.lambda.APICasaDeJairo.models.Doacao;
import com.lambda.APICasaDeJairo.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoacaoServiceImpl implements DoacaoService {
    
    @Autowired
    private DoacaoRepository repository;

    @Override
    public DoacaoDTO criarDoacao(DoacaoDTO dto) {
        Doacao doacao = new Doacao();
        doacao.setNomeDoador(dto.getNomeDoador());
        doacao.setEmail(dto.getEmail());
        doacao.setValor(dto.getValor());
        doacao.setMensagem(dto.getMensagem());

        Doacao salva = repository.save(doacao);

        return new DoacaoDTO(
                salva.getNomeDoador(),
                salva.getEmail(),
                salva.getValor(),
                salva.getMensagem()
        );
    }

    @Override
    public List<DoacaoDTO> listarDoacao() {
        return repository.findAll().stream()
                .map(d -> new DoacaoDTO(
                        d.getNomeDoador(),
                        d.getEmail(),
                        d.getValor(),
                        d.getMensagem()))
                .collect(Collectors.toList());
    }
}
