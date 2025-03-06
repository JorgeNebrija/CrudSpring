package com.example.crudusuario.repository;

import com.example.crudusuario.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository 
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    // Hereda todos los métodos CRUD de JpaRepository sin necesidad de implementarlos manualmente.
}
