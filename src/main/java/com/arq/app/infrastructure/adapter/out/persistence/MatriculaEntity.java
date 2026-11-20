package com.arq.app.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad JPA de la tabla "matriculas". La clave foránea estudiante_id
 * (fk_matricula_estudiante) materializa la relación con estudiantes.
 */
@Entity
@Table(name = "matriculas")
public class MatriculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_matricula_estudiante"))
    private EstudianteEntity estudiante;

    @Column(nullable = false, length = 100)
    private String programa;

    @Column(nullable = false, length = 10)
    private String periodo;

    @Column(nullable = false)
    private Integer semestre;

    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @Column(nullable = false, length = 15)
    private String estado;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    public MatriculaEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EstudianteEntity getEstudiante() { return estudiante; }
    public void setEstudiante(EstudianteEntity estudiante) { this.estudiante = estudiante; }

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
