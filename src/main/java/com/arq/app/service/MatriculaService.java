package com.arq.app.service;

import com.arq.app.model.Matricula;
import com.arq.app.repository.MatriculaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    @Transactional(readOnly = true)
    public List<Matricula> listar() {
        return matriculaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Matricula buscarPorId(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No existe la matricula con id " + id));
    }

    public Matricula guardar(Matricula matricula) {
        Long estudianteId = matricula.getEstudiante().getId();
        String programa = matricula.getPrograma().trim();
        matricula.setPrograma(programa);

        boolean duplicada = (matricula.getId() == null)
                ? matriculaRepository.existsByEstudianteIdAndPrograma(estudianteId, programa)
                : matriculaRepository.existsByEstudianteIdAndProgramaAndIdNot(
                estudianteId, programa, matricula.getId());

        if (duplicada) {
            throw new IllegalArgumentException(
                    "El estudiante ya tiene una matricula en ese programa");
        }
        return matriculaRepository.save(matricula);
    }

    public void aprobar(Long id) {
        cambiarEstado(id, "APROBADA");
    }

    public void rechazar(Long id) {
        cambiarEstado(id, "RECHAZADA");
    }

    private void cambiarEstado(Long id, String nuevoEstado) {
        Matricula matricula = buscarPorId(id);
        matricula.setEstado(nuevoEstado);
        matriculaRepository.save(matricula);
    }

    public void eliminar(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new IllegalArgumentException("No existe la matricula con id " + id);
        }
        matriculaRepository.deleteById(id);
    }
}