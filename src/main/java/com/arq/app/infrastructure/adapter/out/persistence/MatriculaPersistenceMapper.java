package com.arq.app.infrastructure.adapter.out.persistence;

import com.arq.app.domain.model.EstadoMatricula;
import com.arq.app.domain.model.Matricula;
import org.springframework.stereotype.Component;

/** Traduce entre el modelo de dominio Matricula y la entidad JPA MatriculaEntity. */
@Component
public class MatriculaPersistenceMapper {

    private final EstudiantePersistenceMapper estudianteMapper;

    public MatriculaPersistenceMapper(EstudiantePersistenceMapper estudianteMapper) {
        this.estudianteMapper = estudianteMapper;
    }

    public MatriculaEntity toEntity(Matricula matricula) {
        MatriculaEntity entity = new MatriculaEntity();
        entity.setId(matricula.getId());
        entity.setEstudiante(estudianteMapper.toEntity(matricula.getEstudiante()));
        entity.setPrograma(matricula.getPrograma());
        entity.setPeriodo(matricula.getPeriodo());
        entity.setSemestre(matricula.getSemestre());
        entity.setFechaMatricula(matricula.getFechaMatricula());
        entity.setEstado(matricula.getEstado().name());
        entity.setValor(matricula.getValor());
        return entity;
    }

    public Matricula toDomain(MatriculaEntity entity) {
        return new Matricula(
                entity.getId(),
                estudianteMapper.toDomain(entity.getEstudiante()),
                entity.getPrograma(),
                entity.getPeriodo(),
                entity.getSemestre(),
                entity.getFechaMatricula(),
                EstadoMatricula.valueOf(entity.getEstado()),
                entity.getValor());
    }
}
