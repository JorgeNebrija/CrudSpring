package com.example.crudusuario.repository;

import com.example.crudusuario.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TareaRepository extends JpaRepository<Tarea, Long> {

    //Busca todas las tareas de un proyecto específico por su ID
    List<Tarea> findByProyectoId(Long proyectoId);
}
