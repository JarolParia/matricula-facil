package com.arq.app.domain.model;

/**
 * Modelo de dominio: Estudiante.
 * Es una clase Java pura: no conoce JPA, Spring ni la base de datos.
 */
public class Estudiante {

    private Long id;
    private String documento;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;

    public Estudiante() {
    }

    public Estudiante(Long id, String documento, String nombres, String apellidos,
                      String correo, String telefono) {
        this.id = id;
        this.documento = documento;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.telefono = telefono;
    }

    public String getNombreCompleto() {
        return nombres + " " + apellidos;
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
}
