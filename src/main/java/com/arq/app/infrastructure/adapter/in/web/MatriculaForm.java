package com.arq.app.infrastructure.adapter.in.web;

import com.arq.app.domain.model.EstadoMatricula;
import com.arq.app.domain.model.Matricula;
import com.arq.app.domain.port.in.GuardarMatriculaCommand;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

/** Objeto del formulario web de matrícula. Se convierte en un comando del puerto de entrada. */
public class MatriculaForm {

    private Long id;

    @NotNull(message = "Debe seleccionar un estudiante")
    private Long estudianteId;

    @NotBlank(message = "El programa es obligatorio")
    @Size(max = 100)
    private String programa;

    @NotBlank(message = "El periodo es obligatorio")
    @Pattern(regexp = "^\\d{4}-[12]$", message = "El periodo debe tener el formato 2026-1 o 2026-2")
    private String periodo;

    @NotNull(message = "El semestre es obligatorio")
    @Min(value = 1, message = "El semestre mínimo es 1")
    @Max(value = 10, message = "El semestre máximo es 10")
    private Integer semestre;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaMatricula;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|APROBADA|RECHAZADA",
            message = "El estado debe ser PENDIENTE, APROBADA o RECHAZADA")
    private String estado = EstadoMatricula.PENDIENTE.name();

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor que 0")
    @Digits(integer = 10, fraction = 2, message = "El valor admite hasta 10 enteros y 2 decimales")
    private BigDecimal valor;

    public static MatriculaForm desde(Matricula matricula) {
        MatriculaForm form = new MatriculaForm();
        form.id = matricula.getId();
        form.estudianteId = matricula.getEstudiante().getId();
        form.programa = matricula.getPrograma();
        form.periodo = matricula.getPeriodo();
        form.semestre = matricula.getSemestre();
        form.fechaMatricula = matricula.getFechaMatricula();
        form.estado = matricula.getEstado().name();
        form.valor = matricula.getValor();
        return form;
    }

    public GuardarMatriculaCommand aComando() {
        return new GuardarMatriculaCommand(id, estudianteId, programa, periodo, semestre,
                fechaMatricula, EstadoMatricula.valueOf(estado), valor);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEstudianteId() { return estudianteId; }
    public void setEstudianteId(Long estudianteId) { this.estudianteId = estudianteId; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
