package com.arq.app.controller;

import com.arq.app.model.Estudiante;
import com.arq.app.service.EstudianteService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteService estudianteService;

    public EstudianteController(EstudianteService estudianteService) {
        this.estudianteService = estudianteService;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("estudiantes", estudianteService.listar());
        return "estudiantes/lista";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("estudiante", new Estudiante());
        model.addAttribute("titulo", "Registrar estudiante");
        return "estudiantes/formulario";
    }

    @GetMapping("/{id}")
    public String detalle(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("estudiante", estudianteService.buscarConMatriculas(id));
            return "estudiantes/detalle";
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model, RedirectAttributes flash) {
        try {
            model.addAttribute("estudiante", estudianteService.buscarPorId(id));
            model.addAttribute("titulo", "Editar estudiante");
            return "estudiantes/formulario";
        } catch (IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
            return "redirect:/estudiantes";
        }
    }

    @PostMapping("/guardar")
    public String guardar(@Valid @ModelAttribute("estudiante") Estudiante estudiante,
                          BindingResult result, Model model, RedirectAttributes flash) {
        model.addAttribute("titulo", estudiante.getId() == null ? "Registrar estudiante" : "Editar estudiante");
        if (result.hasErrors()) {
            return "estudiantes/formulario";
        }
        try {
            estudianteService.guardar(estudiante);
        } catch (IllegalArgumentException e) {
            result.rejectValue("documento", "duplicado", e.getMessage());
            return "estudiantes/formulario";
        }
        flash.addFlashAttribute("exito", "Estudiante guardado correctamente");
        return "redirect:/estudiantes";
    }

    @PostMapping("/{id}/eliminar")
    public String eliminar(@PathVariable Long id, RedirectAttributes flash) {
        try {
            estudianteService.eliminar(id);
            flash.addFlashAttribute("exito", "Estudiante eliminado correctamente");
        } catch (IllegalStateException | IllegalArgumentException e) {
            flash.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/estudiantes";
    }
}