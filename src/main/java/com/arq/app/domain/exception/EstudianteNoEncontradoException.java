package com.arq.app.domain.exception;

/** El estudiante solicitado no existe. */
public class EstudianteNoEncontradoException extends RuntimeException {

    public EstudianteNoEncontradoException(Long id) {
        super("No existe el estudiante con id " + id);
    }
}
