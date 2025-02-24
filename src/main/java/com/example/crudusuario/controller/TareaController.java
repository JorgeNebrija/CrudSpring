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

/**
 * Controlador para la gestión de tareas.
 * Maneja las solicitudes relacionadas con la creación, edición, eliminación y visualización de tareas.
 */
@Controller // Indica que esta clase es un controlador de Spring MVC
@RequestMapping("/tareas") // Define la ruta base para este controlador
public class TareaController {
    
    private final TareaService tareaService; // Servicio para gestionar tareas
    private final ProyectoService proyectoService; // Servicio para gestionar proyectos asociados a tareas

    /**
     * Constructor que inyecta los servicios de tareas y proyectos.
     */
    public TareaController(TareaService tareaService, ProyectoService proyectoService) {
        this.tareaService = tareaService;
        this.proyectoService = proyectoService;
    }

    /**
     * Lista todas las tareas y las envía a la vista.
     * 
     * @param model Modelo de datos para la vista.
     * @return Vista "tareas/lista".
     */
    @GetMapping
    public String listarTareas(Model model) {
        model.addAttribute("tareas", tareaService.listarTareas());
        return "tareas/lista"; // Devuelve la vista con la lista de tareas
    }

    /**
     * Muestra el formulario para crear una nueva tarea.
     * 
     * @param model Modelo de datos para la vista.
     * @return Vista "tareas/formulario".
     */
    @GetMapping("/crear")
    public String mostrarFormularioCrearTarea(Model model) {
        model.addAttribute("tarea", new Tarea()); // Objeto vacío para el formulario
        model.addAttribute("proyectos", proyectoService.listarProyectos()); // Lista de proyectos disponibles
        return "tareas/formulario"; // Vista con el formulario para crear una tarea
    }

    /**
     * Guarda una nueva tarea en la base de datos.
     * 
     * @param tarea Datos de la tarea recibidos desde el formulario.
     * @return Redirección a la lista de tareas.
     */
    @PostMapping
    public String guardarTarea(@ModelAttribute Tarea tarea) {
        // Validación: La tarea debe estar asociada a un proyecto
        if (tarea.getProyecto() == null || tarea.getProyecto().getId() == null) {
            throw new RuntimeException("La tarea debe estar asociada a un proyecto");
        }
        tareaService.guardarTarea(tarea);
        return "redirect:/tareas"; // Redirige a la lista de tareas después de guardar
    }

    /**
     * Muestra el formulario para editar una tarea existente.
     * 
     * @param id ID de la tarea a editar.
     * @param model Modelo de datos para la vista.
     * @return Vista "tareas/formulario".
     */
    @GetMapping("/editar/{id}")
    public String mostrarFormularioEditarTarea(@PathVariable Long id, Model model) {
        // Busca la tarea en la base de datos
        Tarea tarea = tareaService.obtenerTareaPorId(id)
                .orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
        
        model.addAttribute("tarea", tarea);
        model.addAttribute("proyectos", proyectoService.listarProyectos()); // Lista de proyectos disponibles
        return "tareas/formulario"; // Devuelve el formulario para editar la tarea
    }

    /**
     * Actualiza una tarea existente en la base de datos.
     * 
     * @param id ID de la tarea a actualizar.
     * @param tarea Datos actualizados de la tarea.
     * @return Redirección a la lista de tareas.
     */
    @PostMapping("/actualizar/{id}")
    public String actualizarTarea(@PathVariable Long id, @ModelAttribute Tarea tarea) {
        tareaService.actualizarTarea(id, tarea);
        return "redirect:/tareas"; // Redirige a la lista de tareas después de actualizar
    }

    /**
     * Elimina una tarea de la base de datos.
     * 
     * @param id ID de la tarea a eliminar.
     * @return Redirección a la lista de tareas.
     */
    @GetMapping("/eliminar/{id}")
    public String eliminarTarea(@PathVariable Long id) {
        tareaService.eliminarTarea(id);
        return "redirect:/tareas"; // Redirige a la lista de tareas después de eliminar
    }
}
