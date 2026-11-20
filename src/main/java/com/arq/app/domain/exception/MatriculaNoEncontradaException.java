package com.arq.app.domain.exception;

/** La matrícula solicitada no existe. */
public class MatriculaNoEncontradaException extends RuntimeException {

    public MatriculaNoEncontradaException(Long id) {
        super("No existe la matricula con id " + id);
    }
}
