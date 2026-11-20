package com.arq.app.domain.port.out;

import com.arq.app.domain.model.Estudiante;

import java.util.List;
import java.util.Optional;

/** Puerto de salida: lo que la aplicación necesita para persistir estudiantes. */
public interface EstudianteRepositoryPort {

    List<Estudiante> findAll();

    Optional<Estudiante> findById(Long id);

    Estudiante save(Estudiante estudiante);

    void deleteById(Long id);

    boolean existsByDocumento(String documento);

    boolean existsByDocumentoAndIdNot(String documento, Long id);
}
