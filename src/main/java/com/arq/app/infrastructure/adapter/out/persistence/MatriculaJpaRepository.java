package com.arq.app.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/** Detalle técnico de Spring Data JPA. Solo lo usa el adaptador de persistencia. */
public interface MatriculaJpaRepository extends JpaRepository<MatriculaEntity, Long> {

    List<MatriculaEntity> findByEstudianteId(Long estudianteId);

    boolean existsByEstudianteIdAndPrograma(Long estudianteId, String programa);

    boolean existsByEstudianteIdAndProgramaAndIdNot(Long estudianteId, String programa, Long id);

    long countByEstudianteId(Long estudianteId);
}
