package com.arq.app.domain.exception;

/** Regla de negocio: no se elimina un estudiante que tiene matrículas. */
public class EstudianteConMatriculasException extends RuntimeException {

    public EstudianteConMatriculasException() {
        super("No se puede eliminar el estudiante porque tiene matriculas registradas");
    }
}
