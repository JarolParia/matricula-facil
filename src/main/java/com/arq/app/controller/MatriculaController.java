package com.arq.app.controller;

import com.arq.app.model.Matricula;
import com.arq.app.service.EstudianteService;
import com.arq.app.service.MatriculaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;
    private final EstudianteService estudianteService;

    public MatriculaController(MatriculaService matriculaService, EstudianteService estudianteService) {
        this.matriculaService = matriculaService;
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("matriculas", matriculaService.listar());
        return "matriculas/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        Matricula matricula = new Matricula();
        matricula.setFechaMatricula(LocalDate.now());
        matricula.setEstado("PENDIENTE");
        prepararFormulario(model, matricula, "Registrar matrícula");
        return "matriculas/formulario";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("matricula", matriculaService.buscarPorId(id));
            return "matriculas/detalle";
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/matriculas";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            prepararFormulario(model, matriculaService.buscarPorId(id), "Editar matrícula");
            return "matriculas/formulario";
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/matriculas";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("matricula") Matricula matricula,
                          BindingResult result, Model model, RedirectAttributes flash) {
        String titulo = matricula.getId() == null ? "Registrar matrícula" : "Editar matrícula";
        model.addAttribute("titulo", titulo);
        model.addAttribute("estudiantes", estudianteService.listar());

        Long estudianteId = matricula.getEstudiante() == null ? null : matricula.getEstudiante().getId();
        if (estudianteId == null) {
            result.rejectValue("estudiante", "requerido", "Debe seleccionar un estudiante");
        }
        if (result.hasErrors()) {
            return "matriculas/formulario";
        }
        // Se reemplaza por el estudiante real de la base de datos
        matricula.setEstudiante(estudianteService.buscarPorId(estudianteId));
        try {
            matriculaService.guardar(matricula);
        } catch (IllegalArgumentException e) {
            result.rejectValue("programa", "duplicada", e.getMessage());
            return "matriculas/formulario";
        }
        flash.addFlashAttribute("exito", "Matrícula guardada correctamente");
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/aprobar")
    public String aprobar(@PathVariable Long id, RedirectAttributes flash) {
        matriculaService.aprobar(id);
        flash.addFlashAttribute("exito", "Matrícula aprobada");
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/rechazar")
    public String rechazar(@PathVariable Long id, RedirectAttributes flash) {
        matriculaService.rechazar(id);
        flash.addFlashAttribute("exito", "Matrícula rechazada");
        return "redirect:/matriculas";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            matriculaService.eliminar(id);
            flash.addFlashAttribute("exito", "Matrícula eliminada correctamente");
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/matriculas";
    }

    private void prepararFormulario(Model model, Matricula matricula, String titulo) {
        model.addAttribute("matricula", matricula);
        model.addAttribute("estudiantes", estudianteService.listar());
        model.addAttribute("titulo", titulo);
    }
}