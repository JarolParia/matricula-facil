package com.arq.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^[0-9]{6,10}$",
            message = "El documento debe tener entre 6 y 10 dígitos, solo números")
    @Column(nullable = false, unique = true, length = 20)
    private String documento;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 80)
    @Column(nullable = false, length = 80)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Column(nullable = false, length = 120)
    private String correo;

    @Pattern(regexp = "^[0-9]*$", message = "El teléfono solo debe contener números")
    @Size(max = 15)
    @Column(length = 15)
    private String telefono;

    // Un estudiante tiene muchas matrículas
    @OneToMany(mappedBy = "estudiante")
    private List<Matricula> matriculas = new ArrayList<>();

    public Estudiante() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public List<Matricula> getMatriculas() {
        return matriculas;
    }

    public void setMatriculas(List<Matricula> matriculas) {
        this.matriculas = matriculas;
    }
// Getters y setters (ver nota abajo)
}