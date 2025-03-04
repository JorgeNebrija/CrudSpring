package com.example.crudusuario.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import com.example.crudusuario.model.Tarea;

public interface TareaRepository extends JpaRepository<Tarea, Long> {

    @Query("SELECT t FROM Tarea t JOIN t.proyectos p WHERE p.id = :proyectoId")
    List<Tarea> findByProyecto(@Param("proyectoId") Long proyectoId);
}
