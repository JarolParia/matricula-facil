package com.arq.app.infrastructure.adapter.out.persistence;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA de la tabla "estudiantes". Es un detalle de infraestructura:
 * el dominio no la conoce (usa com.arq.app.domain.model.Estudiante).
 */
@Entity
@Table(name = "estudiantes")
public class EstudianteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @Column(nullable = false, length = 80)
    private String nombres;

    @Column(nullable = false, length = 80)
    private String apellidos;

    @Column(nullable = false, length = 120)
    private String correo;

    @Column(length = 15)
    private String telefono;

    // Un estudiante tiene muchas matrículas (lado inverso de la relación; no se usa en el dominio)
    @OneToMany(mappedBy = "estudiante")
    private List<MatriculaEntity> matriculas = new ArrayList<>();

    public EstudianteEntity() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public List<MatriculaEntity> getMatriculas() { return matriculas; }
    public void setMatriculas(List<MatriculaEntity> matriculas) { this.matriculas = matriculas; }
}
