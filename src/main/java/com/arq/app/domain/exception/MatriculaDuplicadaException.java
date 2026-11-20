package com.arq.app.domain.exception;

/** Regla de negocio: un estudiante solo puede tener una matrícula por programa. */
public class MatriculaDuplicadaException extends RuntimeException {

    public MatriculaDuplicadaException() {
        super("El estudiante ya tiene una matricula en ese programa");
    }
}
