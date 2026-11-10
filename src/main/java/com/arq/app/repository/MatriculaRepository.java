package com.arq.app.repository;

import com.arq.app.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByEstudianteId(Long estudianteId);

    boolean existsByEstudianteIdAndPrograma(Long estudianteId, String programa);

    boolean existsByEstudianteIdAndProgramaAndIdNot(Long estudianteId, String programa, Long id);

    long countByEstudianteId(Long estudianteId);
}