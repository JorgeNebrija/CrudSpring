package com.example.crudusuario.controller;

import com.example.crudusuario.model.EstadoProyecto;
import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.service.ProyectoService;
import com.example.crudusuario.service.TareaService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {
    private final ProyectoService proyectoService;
    private final TareaService tareaService;

    public ProyectoController(ProyectoService proyectoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;
    }

    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "proyectos/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearProyecto(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("tareas", tareaService.listarTareas()); // Para el select de tareas
        return "proyectos/formulario";
    }

   
    @PostMapping
public String guardarProyecto(@ModelAttribute Proyecto proyecto,
                              @RequestParam("estado") String estado,
                              @RequestParam(required = false) List<Long> tareasSeleccionadas) {
    try {
        proyecto.setEstado(EstadoProyecto.valueOf(estado));

        // Si hay tareas seleccionadas, asignarlas correctamente al proyecto
        if (tareasSeleccionadas != null && !tareasSeleccionadas.isEmpty()) {
            List<Tarea> tareas = tareaService.obtenerTareasPorIds(tareasSeleccionadas);
            for (Tarea tarea : tareas) {
                tarea.setProyecto(proyecto);  // Asegurar que cada tarea tenga su proyecto asociado
            }
            proyecto.setTareas(tareas);
        } else {
            proyecto.setTareas(new ArrayList<>());  // Evitar listas nulas
        }

        proyectoService.guardarProyecto(proyecto);
        return "redirect:/proyectos";
    } catch (Exception e) {
        System.out.println("Error al guardar el proyecto: " + e.getMessage());
        return "error"; // Redirigir a una vista de error si hay problemas
    }
}


    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProyecto(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", tareaService.listarTareas());
        return "proyectos/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProyecto(@PathVariable Long id, @ModelAttribute Proyecto proyecto, @RequestParam(required = false) List<Long> tareasSeleccionadas) {
        if (tareasSeleccionadas != null) {
            List<Tarea> tareas = tareaService.obtenerTareasPorIds(tareasSeleccionadas);
            proyecto.setTareas(tareas);
        }
        proyectoService.actualizarProyecto(id, proyecto);
        return "redirect:/proyectos";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return "redirect:/proyectos";
    }

    @GetMapping("/{proyectoId}/tareas")
    public String listarTareasPorProyecto(@PathVariable Long proyectoId, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", tareaService.listarTareasPorProyecto(proyectoId));
        return "tareas/lista";
    }
}
