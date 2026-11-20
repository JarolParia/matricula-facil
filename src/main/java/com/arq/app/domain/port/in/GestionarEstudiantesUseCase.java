package com.arq.app.domain.port.in;

import com.arq.app.domain.model.Estudiante;

import java.util.List;

/** Puerto de entrada: operaciones que la aplicación ofrece sobre los estudiantes. */
public interface GestionarEstudiantesUseCase {

    List<Estudiante> listarEstudiantes();

    /** @throws com.arq.app.domain.exception.EstudianteNoEncontradoException si no existe */
    Estudiante buscarEstudiantePorId(Long id);

    /** @throws com.arq.app.domain.exception.DocumentoDuplicadoException si el documento ya está registrado */
    Estudiante guardarEstudiante(Estudiante estudiante);

    /** @throws com.arq.app.domain.exception.EstudianteConMatriculasException si tiene matrículas */
    void eliminarEstudiante(Long id);
}
