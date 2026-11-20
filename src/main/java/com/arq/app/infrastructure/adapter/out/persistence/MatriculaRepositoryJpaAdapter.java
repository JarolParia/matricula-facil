package com.arq.app.infrastructure.adapter.out.persistence;

import com.arq.app.domain.model.Matricula;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/** Adaptador de salida: implementa el puerto MatriculaRepositoryPort usando Spring Data JPA. */
@Component
@Transactional
public class MatriculaRepositoryJpaAdapter implements MatriculaRepositoryPort {

    private final MatriculaJpaRepository jpaRepository;
    private final MatriculaPersistenceMapper mapper;

    public MatriculaRepositoryJpaAdapter(MatriculaJpaRepository jpaRepository,
                                         MatriculaPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Matricula> findAll() {
        return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Matricula> findByEstudianteId(Long estudianteId) {
        return jpaRepository.findByEstudianteId(estudianteId).stream().map(mapper::toDomain).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Matricula> findById(Long id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Matricula save(Matricula matricula) {
        MatriculaEntity saved = jpaRepository.save(mapper.toEntity(matricula));
        return mapper.toDomain(saved);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEstudianteIdAndPrograma(Long estudianteId, String programa) {
        return jpaRepository.existsByEstudianteIdAndPrograma(estudianteId, programa);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByEstudianteIdAndProgramaAndIdNot(Long estudianteId, String programa, Long id) {
        return jpaRepository.existsByEstudianteIdAndProgramaAndIdNot(estudianteId, programa, id);
    }

    @Override
    @Transactional(readOnly = true)
    public long countByEstudianteId(Long estudianteId) {
        return jpaRepository.countByEstudianteId(estudianteId);
    }
}
