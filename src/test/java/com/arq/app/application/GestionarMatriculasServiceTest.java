package com.arq.app.application;

import com.arq.app.application.service.GestionarMatriculasService;
import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.exception.MatriculaDuplicadaException;
import com.arq.app.domain.exception.MatriculaNoEncontradaException;
import com.arq.app.domain.model.EstadoMatricula;
import com.arq.app.domain.model.Estudiante;
import com.arq.app.domain.model.Matricula;
import com.arq.app.domain.port.in.GuardarMatriculaCommand;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GestionarMatriculasServiceTest {

    @Mock
    private MatriculaRepositoryPort matriculas;
    @Mock
    private EstudianteRepositoryPort estudiantes;
    @InjectMocks
    private GestionarMatriculasService service;

    private final Estudiante ana = new Estudiante(1L, "1234567", "Ana", "Pérez", "ana@correo.com", null);

    private GuardarMatriculaCommand comando(Long id, Long estudianteId, String programa) {
        return new GuardarMatriculaCommand(id, estudianteId, programa, "2026-2", 3,
                LocalDate.of(2026, 11, 10), EstadoMatricula.PENDIENTE, new BigDecimal("1500000"));
    }

    @Test
    void registraUnaMatriculaConElEstudianteDelDominio() {
        when(estudiantes.findById(1L)).thenReturn(Optional.of(ana));
        when(matriculas.existsByEstudianteIdAndPrograma(1L, "Derecho")).thenReturn(false);
        when(matriculas.save(any(Matricula.class))).thenAnswer(i -> i.getArgument(0));

        Matricula guardada = service.guardarMatricula(comando(null, 1L, "  Derecho  "));

        assertEquals("Derecho", guardada.getPrograma(), "el programa se guarda sin espacios sobrantes");
        assertSame(ana, guardada.getEstudiante());
        assertEquals(EstadoMatricula.PENDIENTE, guardada.getEstado());
    }

    @Test
    void rechazaUnEstudianteInexistente() {
        when(estudiantes.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EstudianteNoEncontradoException.class,
                () -> service.guardarMatricula(comando(null, 99L, "Derecho")));
        verify(matriculas, never()).save(any());
    }

    @Test
    void rechazaUnSegundoRegistroEnElMismoPrograma() {
        when(estudiantes.findById(1L)).thenReturn(Optional.of(ana));
        when(matriculas.existsByEstudianteIdAndPrograma(1L, "Derecho")).thenReturn(true);

        assertThrows(MatriculaDuplicadaException.class,
                () -> service.guardarMatricula(comando(null, 1L, "Derecho")));
        verify(matriculas, never()).save(any());
    }

    @Test
    void alEditarNoSeCuentaAsiMisma() {
        Matricula existente = new Matricula(7L, ana, "Derecho", "2026-2", 3,
                LocalDate.of(2026, 11, 10), EstadoMatricula.PENDIENTE, BigDecimal.TEN);
        when(estudiantes.findById(1L)).thenReturn(Optional.of(ana));
        when(matriculas.findById(7L)).thenReturn(Optional.of(existente));
        when(matriculas.existsByEstudianteIdAndProgramaAndIdNot(1L, "Derecho", 7L)).thenReturn(false);
        when(matriculas.save(any(Matricula.class))).thenAnswer(i -> i.getArgument(0));

        Matricula guardada = service.guardarMatricula(comando(7L, 1L, "Derecho"));

        assertEquals(7L, guardada.getId());
    }

    @Test
    void aprobarCambiaElEstadoYGuarda() {
        Matricula m = new Matricula(7L, ana, "Derecho", "2026-2", 3,
                LocalDate.of(2026, 11, 10), EstadoMatricula.PENDIENTE, BigDecimal.TEN);
        when(matriculas.findById(7L)).thenReturn(Optional.of(m));

        service.aprobarMatricula(7L);

        ArgumentCaptor<Matricula> captor = ArgumentCaptor.forClass(Matricula.class);
        verify(matriculas).save(captor.capture());
        assertEquals(EstadoMatricula.APROBADA, captor.getValue().getEstado());
    }

    @Test
    void rechazarCambiaElEstadoYGuarda() {
        Matricula m = new Matricula(7L, ana, "Derecho", "2026-2", 3,
                LocalDate.of(2026, 11, 10), EstadoMatricula.PENDIENTE, BigDecimal.TEN);
        when(matriculas.findById(7L)).thenReturn(Optional.of(m));

        service.rechazarMatricula(7L);

        assertEquals(EstadoMatricula.RECHAZADA, m.getEstado());
        verify(matriculas).save(m);
    }

    @Test
    void eliminarInexistenteLanzaExcepcion() {
        when(matriculas.existsById(5L)).thenReturn(false);
        assertThrows(MatriculaNoEncontradaException.class, () -> service.eliminarMatricula(5L));
    }

    @Test
    void eliminaUnaMatriculaExistente() {
        when(matriculas.existsById(5L)).thenReturn(true);
        service.eliminarMatricula(5L);
        verify(matriculas).deleteById(5L);
    }
}
