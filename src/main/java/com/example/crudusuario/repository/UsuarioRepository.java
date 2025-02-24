package com.example.crudusuario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudusuario.model.Usuario;

/**
 * La interfaz UsuarioRepository es un repositorio JPA que permite realizar 
 * operaciones CRUD (Create, Read, Update, Delete) sobre la entidad Usuario.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Método personalizado para buscar un usuario por su nombre de usuario (username).
     * Spring Data JPA generará automáticamente la consulta en base al nombre del método.
     *
     * @param username Nombre de usuario a buscar.
     * @return Un objeto Optional que puede contener el usuario encontrado.
     */
    Optional<Usuario> findByUsername(String username);
}
