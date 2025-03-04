package com.example.crudusuario.controller;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.service.TareaService;
import com.example.crudusuario.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    List<Tarea> tareas = tareaService.listarTareas();
    model.addAttribute("tareas", tareas);
    return "tareas/lista";
}

    @GetMapping("/crear")
    public String mostrarFormularioCrearTarea(Model model) {
        model.addAttribute("tarea", new Tarea());
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "tareas/formulario";
    }

    @PostMapping
public String guardarTarea(@ModelAttribute Tarea tarea, 
                           @RequestParam(required = false) List<Long> proyectosSeleccionados) {
    if (proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty()) {
        Set<Proyecto> proyectos = new HashSet<>(proyectoService.obtenerProyectosPorIds(proyectosSeleccionados));
        tarea.setProyectos(proyectos);
    } else {
        tarea.setProyectos(new HashSet<>());
    }

    // ✅ Llamar a la versión corregida de guardarTarea
    tareaService.guardarTarea(tarea, proyectosSeleccionados);
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
    public String actualizarTarea(@PathVariable Long id, 
                                  @ModelAttribute Tarea tarea, 
                                  @RequestParam(required = false) List<Long> proyectosSeleccionados) {
        if (proyectosSeleccionados != null) {
            Set<Proyecto> proyectos = new HashSet<>(proyectoService.obtenerProyectosPorIds(proyectosSeleccionados));
            tarea.setProyectos(proyectos);
        }
        tareaService.actualizarTarea(id, tarea, proyectosSeleccionados);
        return "redirect:/tareas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return "redirect:/tareas";
    }
}
