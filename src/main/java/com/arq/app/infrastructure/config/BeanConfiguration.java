package com.arq.app.infrastructure.config;

import com.arq.app.application.service.GestionarEstudiantesService;
import com.arq.app.application.service.GestionarMatriculasService;
import com.arq.app.domain.port.in.GestionarEstudiantesUseCase;
import com.arq.app.domain.port.in.GestionarMatriculasUseCase;
import com.arq.app.domain.port.out.EstudianteRepositoryPort;
import com.arq.app.domain.port.out.MatriculaRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Único lugar donde se "conectan" los casos de uso con sus puertos de salida.
 * Así la capa de aplicación queda libre de anotaciones de Spring.
 */
@Configuration
public class BeanConfiguration {

    @Bean
    public GestionarEstudiantesUseCase gestionarEstudiantesUseCase(
            EstudianteRepositoryPort estudiantes, MatriculaRepositoryPort matriculas) {
        return new GestionarEstudiantesService(estudiantes, matriculas);
    }

    @Bean
    public GestionarMatriculasUseCase gestionarMatriculasUseCase(
            MatriculaRepositoryPort matriculas, EstudianteRepositoryPort estudiantes) {
        return new GestionarMatriculasService(matriculas, estudiantes);
    }
}
