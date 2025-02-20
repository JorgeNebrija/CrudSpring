package com.example.crudusuario.controller;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.service.TareaService;
import com.example.crudusuario.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/tareas")
public class TareaController {
    private final TareaService tareaService;
    private final ProyectoService proyectoService;

    public TareaController(TareaService tareaService, ProyectoService proyectoService) {
        this.tareaService = tareaService;
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listarTareas(Model model) {
        model.addAttribute("tareas", tareaService.listarTareas());
        return "tareas/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearTarea(Model model) {
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "tareas/formulario";
    }

    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea) {
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            throw new RuntimeException("La tarea debe estar asociada a un proyecto");
        }
        tareaService.guardarTarea(tarea);
        return "redirect:/tareas";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarTarea(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.obtenerTareaPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        model.addAttribute("tarea", tarea);
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "tareas/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTarea(@PathVariable Long id, @ModelAttribute Tarea tarea) {
        tareaService.actualizarTarea(id, tarea);
        return "redirect:/tareas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return "redirect:/tareas";
    }
}
