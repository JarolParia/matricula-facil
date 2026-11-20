package com.arq.app.domain.exception;

/** Regla de negocio: el documento del estudiante no puede repetirse. */
public class DocumentoDuplicadoException extends RuntimeException {

    public DocumentoDuplicadoException(String documento) {
        super("Ya existe un estudiante con el documento " + documento);
    }
}
