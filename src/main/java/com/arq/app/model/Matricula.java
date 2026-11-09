package com.arq.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "matriculas")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Muchas matrículas pertenecen a un estudiante (clave foránea)
    @NotNull(message = "Debe seleccionar un estudiante")
    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false,
                foreignKey=@ForeignKey(name="fk_matricula_estudiante"))
    private Estudiante estudiante;

    @NotBlank(message = "El programa es obligatorio")
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String programa;

    @NotBlank(message = "El periodo es obligatorio")
    @Pattern(regexp = "^\\d{4}-[12]$", message = "El periodo debe tener el formato 2026-1 o 2026-2")
    @Column(nullable = false, length = 10)
    private String periodo;

    @NotNull(message = "El semestre es obligatorio")
    @Min(value = 1, message = "El semestre mínimo es 1")
    @Max(value = 10, message = "El semestre máximo es 10")
    @Column(nullable = false)
    private Integer semestre;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Column(name = "fecha_matricula", nullable = false)
    private LocalDate fechaMatricula;

    @NotBlank(message = "El estado es obligatorio")
    @Pattern(regexp = "PENDIENTE|APROBADA|RECHAZADA",
            message = "El estado debe ser PENDIENTE, APROBADA o RECHAZADA")
    @Column(nullable = false, length = 15)
    private String estado = "PENDIENTE";

    @NotNull(message = "El valor es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El valor debe ser mayor que 0")
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal valor;

    public Matricula() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
// Getters y setters (ver nota abajo)
}