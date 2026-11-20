package com.arq.app.domain.port.in;

import com.arq.app.domain.model.Matricula;

import java.util.List;

/** Puerto de entrada: operaciones que la aplicación ofrece sobre las matrículas. */
public interface GestionarMatriculasUseCase {

    List<Matricula> listarMatriculas();

    List<Matricula> listarMatriculasDeEstudiante(Long estudianteId);

    /** @throws com.arq.app.domain.exception.MatriculaNoEncontradaException si no existe */
    Matricula buscarMatriculaPorId(Long id);

    /**
     * @throws com.arq.app.domain.exception.EstudianteNoEncontradoException si el estudiante no existe
     * @throws com.arq.app.domain.exception.MatriculaDuplicadaException si ya tiene matrícula en ese programa
     */
    Matricula guardarMatricula(GuardarMatriculaCommand command);

    void aprobarMatricula(Long id);

    void rechazarMatricula(Long id);

    void eliminarMatricula(Long id);
}
