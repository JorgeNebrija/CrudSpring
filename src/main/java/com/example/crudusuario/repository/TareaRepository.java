package com.example.crudusuario.repository;

import com.example.crudusuario.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * La interfaz TareaRepository es un repositorio JPA que permite realizar 
 * operaciones CRUD (Create, Read, Update, Delete) sobre la entidad Tarea.
 */
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    /**
     * Método personalizado para encontrar todas las tareas asociadas a un proyecto específico.
     * Spring Data JPA generará automáticamente la consulta basada en el nombre del método.
     *
     * @param proyectoId Identificador del proyecto.
     * @return Lista de tareas que pertenecen al proyecto con el ID dado.
     */
    List<Tarea> findByProyectoId(Long proyectoId);
}
