package com.arq.app.service;

import com.arq.app.model.Estudiante;
import com.arq.app.repository.EstudianteRepository;
import com.arq.app.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EstudianteService {

    private final EstudianteRepository estudianteRepository;
    private final MatriculaRepository matriculaRepository;

    public EstudianteService(EstudianteRepository estudianteRepository,
                             MatriculaRepository matriculaRepository) {
        this.estudianteRepository = estudianteRepository;
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional(readOnly = true)
    public List<Estudiante> listar() {
        return estudianteRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Estudiante buscarPorId(Long id) {
        return estudianteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe el estudiante con id " + id));
    }

    public Estudiante guardar(Estudiante estudiante) {
        boolean duplicado = (estudiante.getId() == null)
                ? estudianteRepository.existsByDocumento(estudiante.getDocumento())
                : estudianteRepository.existsByDocumentoAndIdNot(estudiante.getDocumento(), estudiante.getId());

        if (duplicado) {
            throw new IllegalArgumentException("Ya existe un estudiante con el documento " + estudiante.getDocumento());
        }
        return estudianteRepository.save(estudiante);
    }

    public void eliminar(Long id) {
        if (matriculaRepository.countByEstudianteId(id) > 0) {
            throw new IllegalStateException(
                    "No se puede eliminar el estudiante porque tiene matriculas registradas");
        }
        estudianteRepository.deleteById(id);
    }
}