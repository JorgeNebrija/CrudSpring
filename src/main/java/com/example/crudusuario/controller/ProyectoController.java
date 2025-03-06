package com.example.crudusuario.controller;

import com.example.crudusuario.model.EstadoProyecto;
import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.service.ProyectoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/proyectos")
public class ProyectoController {
    
    private final ProyectoService proyectoService;

    public ProyectoController(ProyectoService proyectoService) {
        this.proyectoService = proyectoService;
    }

    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "proyectos/lista";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCrearProyecto(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        return "proyectos/formulario";
    }

    @PostMapping
    public String guardarProyecto(@ModelAttribute Proyecto proyecto,
                                  @RequestParam("estado") String estado) {
        try {
            proyecto.setEstado(EstadoProyecto.valueOf(estado));
            proyectoService.guardarProyecto(proyecto);
            return "redirect:/proyectos";
        } catch (Exception e) {
            System.out.println("Error al guardar el proyecto: " + e.getMessage());
            return "error";
        }
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProyecto(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        return "proyectos/formulario";
    }

    @PostMapping("/actualizar/{id}")
    public String actualizarProyecto(@PathVariable Long id, @ModelAttribute Proyecto proyecto) {
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
        model.addAttribute("tareas", proyecto.getTareas()); 
        return "tareas/lista";
    }
}
