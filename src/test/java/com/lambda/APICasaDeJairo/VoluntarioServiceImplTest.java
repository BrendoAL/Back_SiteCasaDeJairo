package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import com.lambda.APICasaDeJairo.service.VoluntarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VoluntarioServiceImplTest {

    @Mock
    private VoluntarioRepository repository;

    @InjectMocks
    private VoluntarioServiceImpl service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Voluntario criarEntidade() {
        Voluntario v = new Voluntario();
        v.setId(1L);
        v.setNome("João");
        v.setEmail("joao@email.com");
        v.setTelefone("123456789");
        return v;
    }

    private VoluntarioDTO criarDTO() {
        return new VoluntarioDTO("João", "joao@email.com", "123456789");
    }

    @Test
    void deveCriarVoluntarioComSucesso() {
        Voluntario entidade = criarEntidade();
        when(repository.save(any())).thenReturn(entidade);

        VoluntarioDTO dto = criarDTO();
        VoluntarioDTO resultado = service.criarVoluntario(dto);

        assertNotNull(resultado);
        assertEquals(dto.getNome(), resultado.getNome());
        assertEquals(dto.getEmail(), resultado.getEmail());
        assertEquals(dto.getTelefone(), resultado.getTelefone());
        verify(repository).save(any());
    }

    @Test
    void deveListarVoluntarios() {
        Voluntario v1 = criarEntidade();
        Voluntario v2 = new Voluntario();
        v2.setNome("Maria");
        v2.setEmail("maria@email.com");
        v2.setTelefone("987654321");

        when(repository.findAll()).thenReturn(Arrays.asList(v1, v2));

        List<VoluntarioDTO> resultado = service.listarVoluntarios();

        assertEquals(2, resultado.size());
        assertEquals("João", resultado.get(0).getNome());
        assertEquals("Maria", resultado.get(1).getNome());
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverVoluntarios() {
        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<VoluntarioDTO> resultado = service.listarVoluntarios();

        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
    }

    @Test
    void deveBuscarVoluntarioPorIdQuandoExistente() {
        Voluntario entidade = criarEntidade();
        when(repository.findById(1L)).thenReturn(Optional.of(entidade));

        VoluntarioDTO resultado = service.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("João", resultado.getNome());
    }

    @Test
    void deveLancarExcecaoAoBuscarVoluntarioInexistente() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.buscarPorId(99L);
        });

        assertEquals("Voluntário não encontrado", ex.getMessage());
    }

    @Test
    void deveDeletarVoluntarioQuandoIdExistente() {
        when(repository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> service.deletarVoluntario(1L));
        verify(repository).deleteById(1L);
    }

    @Test
    void deveLancarExcecaoAoDeletarVoluntarioInexistente() {
        when(repository.existsById(42L)).thenReturn(false);

        RecursoNaoEncontradoException ex = assertThrows(RecursoNaoEncontradoException.class, () -> {
            service.deletarVoluntario(42L);
        });

        assertEquals("Voluntário não encontrado", ex.getMessage());
        verify(repository, never()).deleteById(any());
    }
}

