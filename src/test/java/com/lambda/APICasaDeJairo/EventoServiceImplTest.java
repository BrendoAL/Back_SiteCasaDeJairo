package com.lambda.APICasaDeJairo;

import com.lambda.APICasaDeJairo.dto.EventoDTO;
import com.lambda.APICasaDeJairo.exceptions.RecursoNaoEncontradoException;
import com.lambda.APICasaDeJairo.models.Evento;
import com.lambda.APICasaDeJairo.models.Voluntario;
import com.lambda.APICasaDeJairo.repository.EventoRepository;
import com.lambda.APICasaDeJairo.repository.VoluntarioRepository;
import com.lambda.APICasaDeJairo.service.EmailService;
import com.lambda.APICasaDeJairo.service.EventoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventoServiceImplTest {

    @InjectMocks
    private EventoServiceImpl eventoService;

    @Mock
    private EventoRepository eventoRepository;

    @Mock
    private VoluntarioRepository voluntarioRepository;

    @Mock
    private EmailService emailService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    private Evento criarEventoEntity() {
        Evento evento = new Evento();
        evento.setTitulo("Título");
        evento.setDescricao("Descrição");
        evento.setData(LocalDate.of(2025, 12, 25));
        evento.setLocal("Local");
        return evento;
    }

    private EventoDTO criarEventoDTO() {
        return new EventoDTO("Título", "Descrição", LocalDate.of(2025, 12, 25), "Local");
    }

    @Test
    void criar_DeveSalvarEDevolverDTO() {
        Evento evento = criarEventoEntity();
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        EventoDTO dto = criarEventoDTO();
        EventoDTO resultado = eventoService.criar(dto);

        assertNotNull(resultado);
        assertEquals(dto.getTitulo(), resultado.getTitulo());
        verify(eventoRepository).save(any(Evento.class));
    }

    @Test
    void listar_DeveRetornarListaDeDTOsOrdenada() {
        List<Evento> eventos = List.of(criarEventoEntity());
        when(eventoRepository.findAllByOrderByDataAsc()).thenReturn(eventos);

        List<EventoDTO> resultado = eventoService.listar();

        assertEquals(1, resultado.size());
        assertEquals("Título", resultado.get(0).getTitulo());
    }

    @Test
    void atualizar_DeveAtualizarEventoExistente() {
        Evento existente = criarEventoEntity();
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(eventoRepository.save(any(Evento.class))).thenReturn(existente);

        EventoDTO novoDTO = new EventoDTO("Novo Título", "Nova Desc", LocalDate.of(2026, 1, 1), "Novo Local");
        EventoDTO resultado = eventoService.atualizar(1L, novoDTO);

        assertEquals("Novo Título", resultado.getTitulo());
        verify(eventoRepository).save(any(Evento.class));
    }

    @Test
    void atualizar_DeveLancarExcecaoQuandoNaoEncontrado() {
        when(eventoRepository.findById(1L)).thenReturn(Optional.empty());

        EventoDTO dto = criarEventoDTO();
        assertThrows(RecursoNaoEncontradoException.class, () -> eventoService.atualizar(1L, dto));
    }

    @Test
    void deletar_DeveDeletarQuandoExiste() {
        when(eventoRepository.existsById(1L)).thenReturn(true);

        eventoService.deletar(1L);

        verify(eventoRepository).deleteById(1L);
    }

    @Test
    void deletar_DeveLancarExcecaoQuandoNaoExiste() {
        when(eventoRepository.existsById(1L)).thenReturn(false);

        assertThrows(RecursoNaoEncontradoException.class, () -> eventoService.deletar(1L));
    }

    @Test
    void deveEnviarEmailParaVoluntariosComNewsletterAtiva() {
        // Simula voluntário com newsletter ativa
        Voluntario voluntario = new Voluntario();
        voluntario.setNome("Rafael Silva");
        voluntario.setEmail("rafael22celio@gmail.com");
        voluntario.setReceberNewsletter(true);

        when(voluntarioRepository.findByReceberNewsletterTrue()).thenReturn(List.of(voluntario));

        // Simula evento salvo
        Evento evento = criarEventoEntity(); // título: "Título"
        when(eventoRepository.save(any(Evento.class))).thenReturn(evento);

        // Executa o método criar (que chama notificarUsuariosSobreEvento)
        eventoService.criar(criarEventoDTO());

        // Verifica se o e-mail foi enviado corretamente com assunto igual ao da implementação real
        verify(emailService, times(1)).enviarEmailSimples(
                eq("rafael22celio@gmail.com"),
                eq("Novo Evento da Casa de Jairo!"), // <- assunto exato que o código usa
                contains("Título") // <- pode ajustar se quiser validar mais partes
        );
    }

}

