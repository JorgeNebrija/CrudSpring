package com.example.crudusuario.service;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Servicio que maneja la lógica de negocio para la entidad Proyecto.
 * Proporciona métodos para listar, obtener, guardar, actualizar y eliminar proyectos.
 */
@Service // Marca esta clase como un servicio gestionado por Spring
public class ProyectoService {
    
    private final ProyectoRepository proyectoRepository; // Repositorio de proyectos

    /**
     * Constructor que inyecta el repositorio de proyectos.
     * @param proyectoRepository Repositorio JPA para acceder a los proyectos.
     */
    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    /**
     * Obtiene la lista de todos los proyectos almacenados en la base de datos.
     * @return Lista de proyectos.
     */
    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    /**
     * Busca un proyecto por su ID.
     * @param id Identificador del proyecto.
     * @return Un Optional que contiene el proyecto si existe.
     */
    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    /**
     * Guarda un nuevo proyecto en la base de datos.
     * @param proyecto Objeto Proyecto a guardar.
     * @return Proyecto guardado.
     */
    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    /**
     * Actualiza un proyecto existente en la base de datos.
     * Si el proyecto no existe, lanza una excepción.
     * @param id Identificador del proyecto a actualizar.
     * @param proyectoActualizado Datos nuevos del proyecto.
     * @return Proyecto actualizado.
     */
    public Proyecto actualizarProyecto(Long id, Proyecto proyectoActualizado) {
        return proyectoRepository.findById(id).map(proyecto -> {
            proyecto.setNombre(proyectoActualizado.getNombre());
            proyecto.setDescripcion(proyectoActualizado.getDescripcion());
            proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
            proyecto.setEstado(proyectoActualizado.getEstado());
            proyecto.setTareas(proyectoActualizado.getTareas());
            return proyectoRepository.save(proyecto);
        }).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    /**
     * Elimina un proyecto de la base de datos por su ID.
     * @param id Identificador del proyecto a eliminar.
     */
    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
}
 /*Aquí se trabaja con la bd y evito que los controladores trabajan con ella.
  * Actuando como intermediario entre el controlador y el repositorio de datos.
  Me encargo de la lógica de negocio relacionada con la gestión de proyectos
  */