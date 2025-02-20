package com.example.crudusuario.repository;

import com.example.crudusuario.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Long> {
    List<Tarea> findByProyectoId(Long proyectoId);  // Encuentra tareas asociadas a un proyecto
}
