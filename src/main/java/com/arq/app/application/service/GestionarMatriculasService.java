package com.arq.app.application.service;

import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.exception.MatriculaDuplicadaException;
import com.arq.app.domain.exception.MatriculaNoEncontradaException;
import com.arq.app.domain.model.Estudiante;
import com.arq.app.domain.model.Matricula;
import com.arq.app.domain.port.in.GestionarMatriculasUseCase;
import com.arq.app.domain.port.in.GuardarMatriculaCommand;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;

import java.util.List;

/**
 * Caso de uso: gestión de matrículas (registro, consulta, flujo de estados y eliminación).
 * Java puro: depende únicamente de puertos de salida.
 */
public class GestionarMatriculasService implements GestionarMatriculasUseCase {

    private final MatriculaRepositoryPort matriculas;
    private final EstudianteRepositoryPort estudiantes;

    public GestionarMatriculasService(MatriculaRepositoryPort matriculas,
                                      EstudianteRepositoryPort estudiantes) {
        this.matriculas = matriculas;
        this.estudiantes = estudiantes;
    }

    @Override
    public List<Matricula> listarMatriculas() {
        return matriculas.findAll();
    }

    @Override
    public List<Matricula> listarMatriculasDeEstudiante(Long estudianteId) {
        return matriculas.findByEstudianteId(estudianteId);
    }

    @Override
    public Matricula buscarMatriculaPorId(Long id) {
        return matriculas.findById(id)
                .orElseThrow(() -> new MatriculaNoEncontradaException(id));
    }

    @Override
    public Matricula guardarMatricula(GuardarMatriculaCommand command) {
        Estudiante estudiante = estudiantes.findById(command.estudianteId())
                .orElseThrow(() -> new EstudianteNoEncontradoException(command.estudianteId()));

        String programa = command.programa().trim();

        boolean duplicada;
        if (command.id() == null) {
            duplicada = matriculas.existsByEstudianteIdAndPrograma(estudiante.getId(), programa);
        } else {
            buscarMatriculaPorId(command.id());
            duplicada = matriculas.existsByEstudianteIdAndProgramaAndIdNot(
                    estudiante.getId(), programa, command.id());
        }
        if (duplicada) {
            throw new MatriculaDuplicadaException();
        }

        Matricula matricula = new Matricula(command.id(), estudiante, programa, command.periodo(),
                command.semestre(), command.fechaMatricula(), command.estado(), command.valor());
        return matriculas.save(matricula);
    }

    @Override
    public void aprobarMatricula(Long id) {
        Matricula matricula = buscarMatriculaPorId(id);
        matricula.aprobar();
        matriculas.save(matricula);
    }

    @Override
    public void rechazarMatricula(Long id) {
        Matricula matricula = buscarMatriculaPorId(id);
        matricula.rechazar();
        matriculas.save(matricula);
    }

    @Override
    public void eliminarMatricula(Long id) {
        if (!matriculas.existsById(id)) {
            throw new MatriculaNoEncontradaException(id);
        }
        matriculas.deleteById(id);
    }
}
