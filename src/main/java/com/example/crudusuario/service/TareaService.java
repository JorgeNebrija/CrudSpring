package com.example.crudusuario.service;

import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Tarea.
 * Proporciona métodos para listar, obtener, guardar, actualizar y eliminar tareas.
 */
@Service // Marca esta clase como un servicio gestionado por Spring
public class TareaService {

    private final TareaRepository tareaRepository; // Repositorio para acceder a la base de datos de tareas

    /**
     * Constructor que inyecta el repositorio de tareas.
     * @param tareaRepository Repositorio JPA para la gestión de tareas.
     */
    public TareaService(TareaRepository tareaRepository) {
        this.tareaRepository = tareaRepository;
    }

    /**
     * Obtiene la lista de todas las tareas almacenadas en la base de datos.
     * @return Lista de tareas.
     */
    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    /**
     * Obtiene todas las tareas asociadas a un proyecto específico.
     * @param proyectoId Identificador del proyecto.
     * @return Lista de tareas pertenecientes al proyecto.
     */
    public List<Tarea> listarTareasPorProyecto(Long proyectoId) {
        return tareaRepository.findByProyectoId(proyectoId);
    }

    /**
     * Busca una tarea por su ID.
     * @param id Identificador de la tarea.
     * @return Un Optional que contiene la tarea si existe.
     */
    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }

    /**
     * Obtiene una lista de tareas por sus identificadores.
     * @param ids Lista de identificadores de tareas.
     * @return Lista de tareas encontradas.
     */
    public List<Tarea> obtenerTareasPorIds(List<Long> ids) {
        return tareaRepository.findAllById(ids);
    }

    /**
     * Guarda una nueva tarea en la base de datos.
     * @param tarea Objeto Tarea a guardar.
     * @return Tarea guardada.
     */
    public Tarea guardarTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }

    /**
     * Actualiza una tarea existente en la base de datos.
     * Si la tarea no existe, lanza una excepción.
     * @param id Identificador de la tarea a actualizar.
     * @param tareaActualizada Datos nuevos de la tarea.
     * @return Tarea actualizada.
     */
    public Tarea actualizarTarea(Long id, Tarea tareaActualizada) {
        return tareaRepository.findById(id).map(tarea -> {
            tarea.setTitulo(tareaActualizada.getTitulo());
            tarea.setDescripcion(tareaActualizada.getDescripcion());
            tarea.setFechaLimite(tareaActualizada.getFechaLimite());
            tarea.setEstado(tareaActualizada.getEstado());
            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    /**
     * Elimina una tarea de la base de datos por su ID.
     * @param id Identificador de la tarea a eliminar.
     */
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
