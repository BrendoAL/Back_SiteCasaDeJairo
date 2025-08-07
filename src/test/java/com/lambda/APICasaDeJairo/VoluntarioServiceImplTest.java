package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.dto.VoluntarioDTO;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import com.lambda.APICasaDeJairo.service.VoluntarioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
}

