package com.arq.app.domain.model;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Modelo de dominio: Matrícula.
 * Cada matrícula pertenece a un estudiante (relación muchos a uno).
 * Incluye el comportamiento propio del negocio: aprobar y rechazar.
 */
public class Matricula {

    private Long id;
    private Estudiante estudiante;
    private String programa;
    private String periodo;
    private Integer semestre;
    private LocalDate fechaMatricula;
    private EstadoMatricula estado = EstadoMatricula.PENDIENTE;
    private BigDecimal valor;

    public Matricula() {
    }

    public Matricula(Long id, Estudiante estudiante, String programa, String periodo,
                     Integer semestre, LocalDate fechaMatricula, EstadoMatricula estado,
                     BigDecimal valor) {
        this.id = id;
        this.estudiante = estudiante;
        this.programa = programa;
        this.periodo = periodo;
        this.semestre = semestre;
        this.fechaMatricula = fechaMatricula;
        this.estado = estado;
        this.valor = valor;
    }

    public void aprobar() {
        this.estado = EstadoMatricula.APROBADA;
    }

    public void rechazar() {
        this.estado = EstadoMatricula.RECHAZADA;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Estudiante getEstudiante() { return estudiante; }
    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    public String getPrograma() { return programa; }
    public void setPrograma(String programa) { this.programa = programa; }

    public String getPeriodo() { return periodo; }
    public void setPeriodo(String periodo) { this.periodo = periodo; }

    public Integer getSemestre() { return semestre; }
    public void setSemestre(Integer semestre) { this.semestre = semestre; }

    public LocalDate getFechaMatricula() { return fechaMatricula; }
    public void setFechaMatricula(LocalDate fechaMatricula) { this.fechaMatricula = fechaMatricula; }

    public EstadoMatricula getEstado() { return estado; }
    public void setEstado(EstadoMatricula estado) { this.estado = estado; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
}
