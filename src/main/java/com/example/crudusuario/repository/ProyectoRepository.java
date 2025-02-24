package com.example.crudusuario.repository;

import com.example.crudusuario.model.Proyecto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * La interfaz ProyectoRepository es un repositorio JPA que permite realizar 
 * operaciones CRUD (Create, Read, Update, Delete) sobre la entidad Proyecto.
 */
@Repository // Indica que esta interfaz es un componente de repositorio de Spring
public interface ProyectoRepository extends JpaRepository<Proyecto, Long> {
    // Hereda todos los métodos CRUD de JpaRepository sin necesidad de implementarlos manualmente.
}
