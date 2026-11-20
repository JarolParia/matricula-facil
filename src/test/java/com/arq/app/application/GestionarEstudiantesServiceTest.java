package com.arq.app.application;

import com.arq.app.application.service.GestionarEstudiantesService;
import com.arq.app.domain.exception.DocumentoDuplicadoException;
import com.arq.app.domain.exception.EstudianteConMatriculasException;
import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.model.Estudiante;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/** Prueba del caso de uso sin Spring ni base de datos: los puertos de salida se simulan con Mockito. */
@ExtendWith(MockitoExtension.class)
class GestionarEstudiantesServiceTest {

    @Mock
    private EstudianteRepositoryPort estudiantes;
    @Mock
    private MatriculaRepositoryPort matriculas;
    @InjectMocks
    private GestionarEstudiantesService service;

    private Estudiante ana() {
        return new Estudiante(null, "1234567", "Ana", "Pérez", "ana@correo.com", null);
    }

    @Test
    void listaLosEstudiantes() {
        when(estudiantes.findAll()).thenReturn(List.of(ana()));
        assertEquals(1, service.listarEstudiantes().size());
    }

    @Test
    void guardaUnEstudianteNuevo() {
        when(estudiantes.existsByDocumento("1234567")).thenReturn(false);
        when(estudiantes.save(any(Estudiante.class))).thenAnswer(i -> i.getArgument(0));

        Estudiante guardado = service.guardarEstudiante(ana());

        assertEquals("Ana", guardado.getNombres());
        verify(estudiantes).save(any(Estudiante.class));
    }

    @Test
    void rechazaDocumentoDuplicadoAlCrear() {
        when(estudiantes.existsByDocumento("1234567")).thenReturn(true);

        assertThrows(DocumentoDuplicadoException.class, () -> service.guardarEstudiante(ana()));
        verify(estudiantes, never()).save(any());
    }

    @Test
    void alActualizarIgnoraSuPropioDocumento() {
        Estudiante existente = ana();
        existente.setId(5L);
        when(estudiantes.existsByDocumentoAndIdNot("1234567", 5L)).thenReturn(false);
        when(estudiantes.save(existente)).thenReturn(existente);

        assertSame(existente, service.guardarEstudiante(existente));
    }

    @Test
    void buscarInexistenteLanzaExcepcion() {
        when(estudiantes.findById(9L)).thenReturn(Optional.empty());
        assertThrows(EstudianteNoEncontradoException.class, () -> service.buscarEstudiantePorId(9L));
    }

    @Test
    void noEliminaUnEstudianteConMatriculas() {
        when(estudiantes.findById(1L)).thenReturn(Optional.of(ana()));
        when(matriculas.countByEstudianteId(1L)).thenReturn(2L);

        assertThrows(EstudianteConMatriculasException.class, () -> service.eliminarEstudiante(1L));
        verify(estudiantes, never()).deleteById(any());
    }

    @Test
    void eliminaUnEstudianteSinMatriculas() {
        when(estudiantes.findById(1L)).thenReturn(Optional.of(ana()));
        when(matriculas.countByEstudianteId(1L)).thenReturn(0L);

        service.eliminarEstudiante(1L);

        verify(estudiantes).deleteById(1L);
    }
}
