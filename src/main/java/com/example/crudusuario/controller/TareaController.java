package com.example.crudusuario.controller;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.service.TareaService;
import com.example.crudusuario.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        model.addAttribute("proyectos", proyectoService.listarProyectos()); //Se listan los proyectos disponibles
        return "tareas/formulario";
    }

    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea, @RequestParam(required = false) Long proyectoId, Model model) {
        if (proyectoId == null || proyectoId <= 0) {
            model.addAttribute("error", "Debes seleccionar un proyecto válido.");
            model.addAttribute("tarea", tarea);
            model.addAttribute("proyectos", proyectoService.listarProyectos()); // Para volver a mostrar los proyectos disponibles
            return "tareas/formulario"; // Volvemos al formulario con el mensaje de error
        }
    
        tareaService.guardarTarea(tarea, proyectoId);
        return "redirect:/tareas";
    }
    

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarTarea(@PathVariable Long id, Model model) {
        Tarea tarea = tareaService.obtenerTareaPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        model.addAttribute("tarea", tarea);
        model.addAttribute("proyectos", proyectoService.listarProyectos()); //Para reasignar la tarea a otro proyecto
        return "tareas/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarTarea(@PathVariable Long id, 
                                  @ModelAttribute Tarea tarea, 
                                  @RequestParam Long proyectoId) { //Se reasigna a un solo proyecto
        tareaService.actualizarTarea(id, tarea, proyectoId);
        return "redirect:/tareas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return "redirect:/tareas";
    }
}
