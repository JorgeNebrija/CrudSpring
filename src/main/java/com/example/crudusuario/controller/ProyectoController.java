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

/**
 * Controlador para la gestión de proyectos.
 * Maneja las solicitudes relacionadas con la creación, edición, eliminación y visualización de proyectos.
 */
@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/proyectos") // Define la ruta base para este controlador
public class ProyectoController {
    
    private final ProyectoService proyectoService; // Servicio para gestionar proyectos
    private final TareaService tareaService; // Servicio para gestionar tareas

    /**
     * Constructor que inyecta los servicios de proyectos y tareas.
     */
    public ProyectoController(ProyectoService proyectoService, TareaService tareaService) {
        this.proyectoService = proyectoService;
        this.tareaService = tareaService;
    }

    /**
     * Lista todos los proyectos y los envía a la vista.
     * 
     * @param model Modelo de datos para la vista.
     * @return Vista "proyectos/lista".
     */
    @GetMapping
    public String listarProyectos(Model model) {
        model.addAttribute("proyectos", proyectoService.listarProyectos());
        return "proyectos/lista"; // Devuelve la vista con la lista de proyectos
    }

    /**
     * Muestra el formulario para crear un nuevo proyecto.
     * 
     * @param model Modelo de datos para la vista.
     * @return Vista "proyectos/formulario".
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrearProyecto(Model model) {
        model.addAttribute("proyecto", new Proyecto());
        model.addAttribute("tareas", tareaService.listarTareas()); // Lista de tareas disponibles para asignar
        return "proyectos/formulario"; // Vista con el formulario para crear un proyecto
    }

    /**
     * Guarda un nuevo proyecto en la base de datos.
     * 
     * @param proyecto Datos del proyecto recibido desde el formulario.
     * @param estado Estado del proyecto recibido como parámetro.
     * @param tareasSeleccionadas Lista de IDs de tareas seleccionadas para el proyecto.
     * @return Redirección a la lista de proyectos.
     */
    @PostMapping
    public String guardarProyecto(@ModelAttribute Proyecto proyecto,
                                  @RequestParam("estado") String estado,
                                  @RequestParam(required = false) List<Long> tareasSeleccionadas) {
        try {
            // Convierte el estado del proyecto de String a Enum
            proyecto.setEstado(EstadoProyecto.valueOf(estado));

            // Asigna tareas al proyecto si se seleccionaron
            if (tareasSeleccionadas != null && !tareasSeleccionadas.isEmpty()) {
                List<Tarea> tareas = tareaService.obtenerTareasPorIds(tareasSeleccionadas);
                for (Tarea tarea : tareas) {
                    tarea.setProyecto(proyecto); // Asigna el proyecto a cada tarea
                }
                proyecto.setTareas(tareas);
            } else {
                proyecto.setTareas(new ArrayList<>()); // Evita listas nulas
            }

            proyectoService.guardarProyecto(proyecto);
            return "redirect:/proyectos"; // Redirige a la lista de proyectos después de guardar
        } catch (Exception e) {
            System.out.println("Error al guardar el proyecto: " + e.getMessage());
            return "error"; // Redirige a una vista de error si hay problemas
        }
    }

    /**
     * Muestra el formulario para editar un proyecto existente.
     * 
     * @param id ID del proyecto a editar.
     * @param model Modelo de datos para la vista.
     * @return Vista "proyectos/formulario".
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarProyecto(@PathVariable Long id, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(id)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", tareaService.listarTareas()); // Lista de tareas disponibles
        return "proyectos/formulario"; // Devuelve el formulario para editar el proyecto
    }

    /**
     * Actualiza un proyecto existente en la base de datos.
     * 
     * @param id ID del proyecto a actualizar.
     * @param proyecto Datos actualizados del proyecto.
     * @param tareasSeleccionadas Lista de IDs de tareas seleccionadas para el proyecto.
     * @return Redirección a la lista de proyectos.
     */
    @PostMapping("/actualizar/{id}")
    public String actualizarProyecto(@PathVariable Long id, @ModelAttribute Proyecto proyecto, 
                                     @RequestParam(required = false) List<Long> tareasSeleccionadas) {
        if (tareasSeleccionadas != null) {
            List<Tarea> tareas = tareaService.obtenerTareasPorIds(tareasSeleccionadas);
            proyecto.setTareas(tareas);
        }
        proyectoService.actualizarProyecto(id, proyecto);
        return "redirect:/proyectos"; // Redirige a la lista de proyectos después de actualizar
    }

    /**
     * Elimina un proyecto de la base de datos.
     * 
     * @param id ID del proyecto a eliminar.
     * @return Redirección a la lista de proyectos.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarProyecto(@PathVariable Long id) {
        proyectoService.eliminarProyecto(id);
        return "redirect:/proyectos"; // Redirige a la lista de proyectos después de eliminar
    }

    /**
     * Lista todas las tareas asociadas a un proyecto específico.
     * 
     * @param proyectoId ID del proyecto.
     * @param model Modelo de datos para la vista.
     * @return Vista "tareas/lista".
     */
    @GetMapping("/{proyectoId}/tareas")
    public String listarTareasPorProyecto(@PathVariable Long proyectoId, Model model) {
        Proyecto proyecto = proyectoService.obtenerProyectoPorId(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        model.addAttribute("proyecto", proyecto);
        model.addAttribute("tareas", tareaService.listarTareasPorProyecto(proyectoId));
        return "tareas/lista"; // Devuelve la vista con la lista de tareas del proyecto
    }
}
