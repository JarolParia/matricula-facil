package com.arq.app.domain.port.in;

import com.arq.app.domain.model.EstadoMatricula;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Datos que necesita el caso de uso para registrar o actualizar una matrícula.
 * Si {@code id} es null se crea una nueva; si no, se actualiza la existente.
 * El estudiante llega solo como identificador: el caso de uso lo busca a través del puerto de salida.
 */
public record GuardarMatriculaCommand(
        Long id,
        Long estudianteId,
        String programa,
        String periodo,
        Integer semestre,
        LocalDate fechaMatricula,
        EstadoMatricula estado,
        BigDecimal valor) {
}
