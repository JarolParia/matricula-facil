package com.arq.app.infrastructure.adapter.in.web;

import com.arq.app.domain.exception.DocumentoDuplicadoException;
import com.arq.app.domain.exception.EstudianteConMatriculasException;
import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.port.in.GestionarEstudiantesUseCase;
import com.arq.app.domain.port.in.GestionarMatriculasUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Adaptador de entrada (web) para estudiantes.
 * Solo depende de puertos de entrada: no conoce repositorios ni entidades JPA.
 */
@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final GestionarEstudiantesUseCase estudiantes;
    private final GestionarMatriculasUseCase matriculas;

    public EstudianteController(GestionarEstudiantesUseCase estudiantes,
                                GestionarMatriculasUseCase matriculas) {
        this.estudiantes = estudiantes;
        this.matriculas = matriculas;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudiantes.listarEstudiantes());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new EstudianteForm());
        model.addAttribute("titulo", "Registrar estudiante");
        return "estudiantes/formulario";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("estudiante", estudiantes.buscarEstudiantePorId(id));
            model.addAttribute("matriculas", matriculas.listarMatriculasDeEstudiante(id));
            return "estudiantes/detalle";
        } catch (EstudianteNoEncontradoException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("estudiante", EstudianteForm.desde(estudiantes.buscarEstudiantePorId(id)));
            model.addAttribute("titulo", "Editar estudiante");
            return "estudiantes/formulario";
        } catch (EstudianteNoEncontradoException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") EstudianteForm form,
                          BindingResult result, Model model, RedirectAttributes flash) {
        model.addAttribute("titulo", form.getId() == null ? "Registrar estudiante" : "Editar estudiante");
        if (result.hasErrors()) {
            return "estudiantes/formulario";
        }
        try {
            estudiantes.guardarEstudiante(form.aDominio());
        } catch (DocumentoDuplicadoException e) {
            result.rejectValue("documento", "duplicado", e.getMessage());
            return "estudiantes/formulario";
        }
        flash.addFlashAttribute("exito", "Estudiante guardado correctamente");
        return "redirect:/estudiantes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            estudiantes.eliminarEstudiante(id);
            flash.addFlashAttribute("exito", "Estudiante eliminado correctamente");
        } catch (EstudianteConMatriculasException | EstudianteNoEncontradoException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/estudiantes";
    }
}
