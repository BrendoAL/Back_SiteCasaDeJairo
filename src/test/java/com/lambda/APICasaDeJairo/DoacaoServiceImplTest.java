package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.dto.DoacaoDTO;
import com.lambda.APICasaDeJairo.exceptions.DadosInvalidosException;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Doacao;
import com.lambda.APICasaDeJairo.repository.DoacaoRepository;
import com.lambda.APICasaDeJairo.service.DoacaoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DoacaoServiceImplTest {

    @Mock
    private DoacaoRepository repository;

    @InjectMocks
    private DoacaoServiceImpl doacaoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveCriarDoacaoComValorValido() {
        DoacaoDTO dto = new DoacaoDTO("Nome do Doador", BigDecimal.valueOf(100.0), "Pix", "Obrigado pela ajuda!");

        Doacao doacao = new Doacao();
        doacao.setValor(BigDecimal.valueOf(100.0));
        doacao.setNomeDoador("Nome do Doador");
        doacao.setMetodoPagamento("Pix");
        doacao.setMensagem("Obrigado pela ajuda!");

        when(repository.save(any())).thenReturn(doacao);

        DoacaoDTO resultado = doacaoService.criarDoacao(dto);

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(100.0), resultado.getValor());
        assertEquals("Nome do Doador", resultado.getNomeDoador());
        assertEquals("Pix", resultado.getMetodoPagamento());
        assertEquals("Obrigado pela ajuda!", resultado.getMensagem());
    }

    @Test
    public void deveLancarExcecaoParaValorNegativo() {
        DoacaoDTO dto = new DoacaoDTO("Nome do Doador", BigDecimal.valueOf(-50.0), "Pix", "Obrigado pela ajuda!");

        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class, () -> {
            doacaoService.criarDoacao(dto);
        });

        assertEquals("O valor da doação deve ser maior que zero.", ex.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaValorZero() {
        DoacaoDTO dto = new DoacaoDTO("Nome do Doador", BigDecimal.valueOf(0), "Pix", "Obrigado pela ajuda!");

        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class, () -> {
            doacaoService.criarDoacao(dto);
        });

        assertEquals("O valor da doação deve ser maior que zero.", ex.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaValorNulo() {
        DoacaoDTO dto = new DoacaoDTO("Nome",null, "Pix", "Mensagem");

        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class, () -> {
            doacaoService.criarDoacao(dto);
        });

        assertEquals("O valor da doação deve ser maior que zero.", ex.getMessage());
    }


    @Test
    public void deveLancarExcecaoParaNomeNulo() {
        DoacaoDTO dto = new DoacaoDTO(null, BigDecimal.valueOf(100.0), "Pix", "Mensagem");

        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class, () -> {
            doacaoService.criarDoacao(dto);
        });

        assertEquals("O nome do doador não pode ser nulo ou vazio.", ex.getMessage());
    }

    @Test
    public void deveLancarExcecaoParaPagamentoNulluOuInvalido() {
        DoacaoDTO dto = new DoacaoDTO("nome", BigDecimal.valueOf(100.0), null, "Mensagem");

        DadosInvalidosException ex = assertThrows(DadosInvalidosException.class, () -> {
            doacaoService.criarDoacao(dto);
        });

        assertEquals("O método de pagamento é obrigatório.", ex.getMessage());
    }

    @Test
    public void deveRetornarDoacaoDTOQuandoIdExistente() {
        Long id = 1L;

        Doacao doacao = new Doacao();
        doacao.setId(id);
        doacao.setValor(BigDecimal.valueOf(150.0));
        doacao.setNomeDoador("João");
        doacao.setMetodoPagamento("Cartão");
        doacao.setMensagem("Doação para o projeto");

        when(repository.findById(id)).thenReturn(Optional.of(doacao));

        DoacaoDTO resultado = doacaoService.buscarPorId(id); 

        assertNotNull(resultado);
        assertEquals(BigDecimal.valueOf(150.0), resultado.getValor());
        assertEquals("João", resultado.getNomeDoador());
        assertEquals("Cartão", resultado.getMetodoPagamento());
        assertEquals("Doação para o projeto", resultado.getMensagem());
    }

    @Test
    public void deveLancarExcecaoQuandoIdNaoExistir() {
        Long id = 99L;

        // Simula que nenhuma doação é encontrada para o ID fornecido
        when(repository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            doacaoService.buscarPorId(id);
        });

        assertEquals("Doação não encontrada", ex.getMessage());
    }

    @Test
    public void deveRetornarListaDeDoacoesDTO() {
        Doacao d1 = new Doacao();
        d1.setNomeDoador("João");
        d1.setValor(BigDecimal.valueOf(100.0));
        d1.setMetodoPagamento("Pix");
        d1.setMensagem("Ajuda mensal");

        Doacao d2 = new Doacao();
        d2.setNomeDoador("Maria");
        d2.setValor(BigDecimal.valueOf(200.0));
        d2.setMetodoPagamento("Boleto");
        d2.setMensagem("Doação especial");

        List<Doacao> listaFalsa = Arrays.asList(d1, d2);

        when(repository.findAll()).thenReturn(listaFalsa);

        List<DoacaoDTO> resultado = doacaoService.listarDoacao();

        assertEquals(2, resultado.size());

        assertEquals("João", resultado.get(0).getNomeDoador());
        assertEquals(BigDecimal.valueOf(100.0), resultado.get(0).getValor());

        assertEquals("Maria", resultado.get(1).getNomeDoador());
        assertEquals(BigDecimal.valueOf(200.0), resultado.get(1).getValor());
    }

    @Test
    public void deveRetornarListaVaziaQuandoNaoHouverDoacoes() {
        // Simula o repositório retornando uma lista vazia
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<DoacaoDTO> resultado = doacaoService.listarDoacao();

        assertNotNull(resultado);           // A lista não deve ser nula
        assertTrue(resultado.isEmpty());   // A lista deve estar vazia
    }

    @Test
    public void deveExcluirDoacaoQuandoIdExistente() {
        Long id = 1L;

        Doacao doacao = new Doacao();
        doacao.setId(id);
        doacao.setNomeDoador("João");
        doacao.setValor(BigDecimal.valueOf(100.0));

        when(repository.existsById(id)).thenReturn(true);

        // Executa o método de exclusão
        assertDoesNotThrow(() -> doacaoService.deletarDoacao(id));

        // Verifica se o método delete foi chamado corretamente
        verify(repository, times(1)).deleteById(id);
    }

    @Test
    public void deveLancarExcecaoAoExcluirDoacaoInexistente() {
        Long id = 99L;

        when(repository.findById(id)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            doacaoService.deletarDoacao(id);
        });

        assertEquals("Doação não encontrada", ex.getMessage());

        // Garante que delete não foi chamado
        verify(repository, never()).delete(any());
    }




}
