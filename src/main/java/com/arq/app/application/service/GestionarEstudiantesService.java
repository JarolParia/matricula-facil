package com.arq.app.application.service;

import com.arq.app.domain.exception.DocumentoDuplicadoException;
import com.arq.app.domain.exception.EstudianteConMatriculasException;
import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.model.Estudiante;
import com.arq.app.domain.port.in.GestionarEstudiantesUseCase;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;

import java.util.List;

/**
 * Caso de uso: gestión de estudiantes.
 * Java puro: solo conoce los puertos (interfaces) del dominio, nunca JPA ni Spring Data.
 */
public class GestionarEstudiantesService implements GestionarEstudiantesUseCase {

    private final EstudianteRepositoryPort estudiantes;
    private final MatriculaRepositoryPort matriculas;

    public GestionarEstudiantesService(EstudianteRepositoryPort estudiantes,
                                       MatriculaRepositoryPort matriculas) {
        this.estudiantes = estudiantes;
        this.matriculas = matriculas;
    }

    @Override
    public List<Estudiante> listarEstudiantes() {
        return estudiantes.findAll();
    }

    @Override
    public Estudiante buscarEstudiantePorId(Long id) {
        return estudiantes.findById(id)
                .orElseThrow(() -> new EstudianteNoEncontradoException(id));
    }

    @Override
    public Estudiante guardarEstudiante(Estudiante estudiante) {
        boolean duplicado = (estudiante.getId() == null)
                ? estudiantes.existsByDocumento(estudiante.getDocumento())
                : estudiantes.existsByDocumentoAndIdNot(estudiante.getDocumento(), estudiante.getId());

        if (duplicado) {
            throw new DocumentoDuplicadoException(estudiante.getDocumento());
        }
        return estudiantes.save(estudiante);
    }

    @Override
    public void eliminarEstudiante(Long id) {
        buscarEstudiantePorId(id);
        if (matriculas.countByEstudianteId(id) > 0) {
            throw new EstudianteConMatriculasException();
        }
        estudiantes.deleteById(id);
    }
}
