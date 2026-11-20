package com.arq.app.infrastructure.adapter.in.web;

import com.arq.app.domain.exception.EstudianteNoEncontradoException;
import com.arq.app.domain.exception.MatriculaDuplicadaException;
import com.arq.app.domain.exception.MatriculaNoEncontradaException;
import com.arq.app.domain.port.in.GestionarEstudiantesUseCase;
import com.arq.app.domain.port.in.GestionarMatriculasUseCase;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * Adaptador de entrada (web) para matrículas.
 * Solo depende de puertos de entrada: no conoce repositorios ni entidades JPA.
 */
@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final GestionarMatriculasUseCase matriculas;
    private final GestionarEstudiantesUseCase estudiantes;

    public MatriculaController(GestionarMatriculasUseCase matriculas,
                               GestionarEstudiantesUseCase estudiantes) {
        this.matriculas = matriculas;
        this.estudiantes = estudiantes;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculas.listarMatriculas());
        return "matriculas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        MatriculaForm form = new MatriculaForm();
        form.setFechaMatricula(LocalDate.now());
        prepararFormulario(model, form, "Registrar matrícula");
        return "matriculas/formulario";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("matricula", matriculas.buscarMatriculaPorId(id));
            return "matriculas/detalle";
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/matriculas";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            MatriculaForm form = MatriculaForm.desde(matriculas.buscarMatriculaPorId(id));
            prepararFormulario(model, form, "Editar matrícula");
            return "matriculas/formulario";
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/matriculas";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("matricula") MatriculaForm form,
                          BindingResult result, Model model, RedirectAttributes flash) {
        model.addAttribute("titulo", form.getId() == null ? "Registrar matrícula" : "Editar matrícula");
        model.addAttribute("estudiantes", estudiantes.listarEstudiantes());
        if (result.hasErrors()) {
            return "matriculas/formulario";
        }
        try {
            matriculas.guardarMatricula(form.aComando());
        } catch (MatriculaDuplicadaException e) {
            result.rejectValue("programa", "duplicada", e.getMessage());
            return "matriculas/formulario";
        } catch (EstudianteNoEncontradoException e) {
            result.rejectValue("estudianteId", "inexistente", e.getMessage());
            return "matriculas/formulario";
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/matriculas";
        }
        flash.addFlashAttribute("exito", "Matrícula guardada correctamente");
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            matriculas.aprobarMatricula(id);
            flash.addFlashAttribute("exito", "Matrícula aprobada");
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            matriculas.rechazarMatricula(id);
            flash.addFlashAttribute("exito", "Matrícula rechazada");
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            matriculas.eliminarMatricula(id);
            flash.addFlashAttribute("exito", "Matrícula eliminada correctamente");
        } catch (MatriculaNoEncontradaException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matriculas";
    }

    private void prepararFormulario(Model model, MatriculaForm form, String titulo) {
        model.addAttribute("matricula", form);
        model.addAttribute("estudiantes", estudiantes.listarEstudiantes());
        model.addAttribute("titulo", titulo);
    }
}
