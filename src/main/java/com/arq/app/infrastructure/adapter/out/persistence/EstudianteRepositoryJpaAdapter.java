package com.arq.app.infrastructure.adapter.out.persistence;

import com.arq.app.domain.model.Estudiante;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Adaptador de salida: implementa el puerto EstudianteRepositoryPort usando Spring Data JPA. */
@Component
@Transactional
public class EstudianteRepositoryJpaAdapter implements EstudianteRepositoryPort {

    private final EstudianteJpaRepository jpaRepository;
    private final EstudiantePersistenceMapper mapper;

    public EstudianteRepositoryJpaAdapter(EstudianteJpaRepository jpaRepository,
                                          EstudiantePersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Estudiante save(Estudiante estudiante) {
        EstudianteEntity saved = jpaRepository.save(mapper.toEntity(estudiante));
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDocumento(String documento) {
        return jpaRepository.existsByDocumento(documento);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByDocumentoAndIdNot(String documento, Long id) {
        return jpaRepository.existsByDocumentoAndIdNot(documento, id);
    }
}
