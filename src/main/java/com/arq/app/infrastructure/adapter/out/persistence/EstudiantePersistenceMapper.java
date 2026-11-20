package com.arq.app.infrastructure.adapter.out.persistence;

import com.arq.app.domain.model.Estudiante;
import org.springframework.stereotype.Component;

/** Traduce entre el modelo de dominio Estudiante y la entidad JPA EstudianteEntity. */
@Component
public class EstudiantePersistenceMapper {

    public EstudianteEntity toEntity(Estudiante estudiante) {
        EstudianteEntity entity = new EstudianteEntity();
        entity.setId(estudiante.getId());
        entity.setDocumento(estudiante.getDocumento());
        entity.setNombres(estudiante.getNombres());
        entity.setApellidos(estudiante.getApellidos());
        entity.setCorreo(estudiante.getCorreo());
        entity.setTelefono(estudiante.getTelefono());
        return entity;
    }

    public Estudiante toDomain(EstudianteEntity entity) {
        return new Estudiante(
                entity.getId(),
                entity.getDocumento(),
                entity.getNombres(),
                entity.getApellidos(),
                entity.getCorreo(),
                entity.getTelefono());
    }
}
