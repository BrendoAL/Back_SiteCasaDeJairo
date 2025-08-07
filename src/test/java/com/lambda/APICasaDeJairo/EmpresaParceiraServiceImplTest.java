package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.dto.EmpresaParceiraDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.EmpresaParceira;
import com.lambda.APICasaDeJairo.repository.EmpresaParceiraRepository;
import com.lambda.APICasaDeJairo.service.EmpresaParceiraServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EmpresaParceiraServiceImplTest {

    @Mock
    private EmpresaParceiraRepository repository;

    @InjectMocks
    private EmpresaParceiraServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private EmpresaParceira criarEntidade() {
        EmpresaParceira empresa = new EmpresaParceira();
        empresa.setId(1L);
        empresa.setNome("Empresa Teste");
        empresa.setEmail("contato@empresa.com");
        empresa.setTelefone("123456789");
        return empresa;
    }

    private EmpresaParceiraDTO criarDTO() {
        return new EmpresaParceiraDTO("Empresa Teste", "contato@empresa.com", "123456789");
    }

    @Test
    void deveCriarEmpresaParceiraComSucesso() {
        EmpresaParceira entidade = criarEntidade();
        when(repository.save(any())).thenReturn(entidade);

        EmpresaParceiraDTO dto = criarDTO();
        EmpresaParceiraDTO resultado = service.criarEmpresaParceira(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNome(), resultado.getNome());
        assertEquals(dto.getEmail(), resultado.getEmail());
        assertEquals(dto.getTelefone(), resultado.getTelefone());
        verify(repository).save(any());
    }

    @Test
    void deveBuscarEmpresaPorIdQuandoExistente() {
        EmpresaParceira entidade = criarEntidade();
        when(repository.findById(1L)).thenReturn(Optional.of(entidade));

        EmpresaParceiraDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Empresa Teste", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoQuandoEmpresaNaoExistirAoBuscarPorId() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.buscarPorId(99L);
        });

        assertEquals("Empresa parceira não encontrada", ex.getMessage());
    }

    @Test
    void deveListarEmpresasParceiras() {
        EmpresaParceira e1 = criarEntidade();
        EmpresaParceira e2 = new EmpresaParceira();
        e2.setNome("Empresa 2");
        e2.setEmail("e2@empresa.com");
        e2.setTelefone("000000000");

        when(repository.findAll()).thenReturn(Arrays.asList(e1, e2));

        List<EmpresaParceiraDTO> resultado = service.listarEmpresaParceira();

        assertEquals(2, resultado.size());
        assertEquals("Empresa Teste", resultado.get(0).getNome());
        assertEquals("Empresa 2", resultado.get(1).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverEmpresas() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<EmpresaParceiraDTO> resultado = service.listarEmpresaParceira();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveAtualizarEmpresaParceiraQuandoExistente() {
        EmpresaParceira existente = criarEntidade();
        when(repository.findById(1L)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenReturn(existente);

        EmpresaParceiraDTO novoDTO = new EmpresaParceiraDTO("Nova Empresa", "nova@empresa.com", "88888888");
        EmpresaParceiraDTO atualizado = service.atualizar(1L, novoDTO);

        assertEquals("Nova Empresa", atualizado.getNome());
        assertEquals("nova@empresa.com", atualizado.getEmail());
        assertEquals("88888888", atualizado.getTelefone());
        verify(repository).save(any());
    }

    @Test
    void deveLancarExcecaoAoAtualizarEmpresaInexistente() {
        when(repository.findById(42L)).thenReturn(Optional.empty());

        EmpresaParceiraDTO dto = criarDTO();

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.atualizar(42L, dto);
        });

        assertEquals("Empresa parceira não encontrada", ex.getMessage());
    }

    @Test
    void deveDeletarEmpresaQuandoIdExiste() {
        when(repository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deletar(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarEmpresaInexistente() {
        when(repository.existsById(999L)).thenReturn(false);

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.deletar(999L);
        });

        assertEquals("Empresa parceira não encontrada", ex.getMessage());
        verify(repository, never()).deleteById(any());
    }
}

