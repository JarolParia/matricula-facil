package com.arq.app.domain.port.out;

import com.arq.app.domain.model.Matricula;

import java.util.List;
import java.util.Optional;

/** Puerto de salida: lo que la aplicación necesita para persistir matrículas. */
public interface MatriculaRepositoryPort {

    List<Matricula> findAll();

    List<Matricula> findByEstudianteId(Long estudianteId);

    Optional<Matricula> findById(Long id);

    Matricula save(Matricula matricula);

    void deleteById(Long id);

    boolean existsById(Long id);

    boolean existsByEstudianteIdAndPrograma(Long estudianteId, String programa);

    boolean existsByEstudianteIdAndProgramaAndIdNot(Long estudianteId, String programa, Long id);

    long countByEstudianteId(Long estudianteId);
}
