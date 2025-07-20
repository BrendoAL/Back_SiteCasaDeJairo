package com.lambda.APICasaDeJairo.service;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;
import com.lambda.APICasaDeJairo.models.Doacao;
import com.lambda.APICasaDeJairo.repository.DoacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//regras de negocio da doacao
@Service
public class DoacaoServiceImpl implements DoacaoService {
    
    @Autowired
    private DoacaoRepository repository;

    @Override
    public DoacaoDTO criarDoacao(DoacaoDTO dto) {
        Doacao doacao = new Doacao();
        doacao.setNomeDoador(dto.getNomeDoador());
        doacao.setValor(dto.getValor());
        doacao.setMetodoPagamento(dto.getMetodoPagamento());
        doacao.setMensagem(dto.getMensagem());

        Doacao salva = repository.save(doacao);

        return new DoacaoDTO(
                salva.getNomeDoador(),
                salva.getValor(),
                salva.getMetodoPagamento(),
                salva.getMensagem()
        );
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
}
