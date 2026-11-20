package com.arq.app.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

/** Detalle técnico de Spring Data JPA. Solo lo usa el adaptador de persistencia. */
public interface EstudianteJpaRepository extends JpaRepository<EstudianteEntity, Long> {

    boolean existsByDocumento(String documento);

    boolean existsByDocumentoAndIdNot(String documento, Long id);
}
