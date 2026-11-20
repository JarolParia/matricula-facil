package com.arq.app.infrastructure.adapter.in.web;

import com.arq.app.domain.model.Estudiante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Objeto del formulario web. Las validaciones de entrada (formato de los campos)
 * viven aquí, en el adaptador, y no en el modelo de dominio.
 */
public class EstudianteForm {

    private Long id;

    @NotBlank(message = "El documento es obligatorio")
    @Pattern(regexp = "^[0-9]{6,10}$",
            message = "El documento debe tener entre 6 y 10 dígitos, solo números")
    private String documento;

    @NotBlank(message = "Los nombres son obligatorios")
    @Size(max = 80)
    private String nombres;

    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 80)
    private String apellidos;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    @Size(max = 120)
    private String correo;

    @Pattern(regexp = "^[0-9]*$", message = "El teléfono solo debe contener números")
    @Size(max = 15)
    private String telefono;

    public static EstudianteForm desde(Estudiante estudiante) {
        EstudianteForm form = new EstudianteForm();
        form.id = estudiante.getId();
        form.documento = estudiante.getDocumento();
        form.nombres = estudiante.getNombres();
        form.apellidos = estudiante.getApellidos();
        form.correo = estudiante.getCorreo();
        form.telefono = estudiante.getTelefono();
        return form;
    }

    public Estudiante aDominio() {
        return new Estudiante(id, documento, nombres, apellidos, correo, telefono);
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
