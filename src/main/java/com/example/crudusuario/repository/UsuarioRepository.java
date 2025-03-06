package com.example.crudusuario.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.crudusuario.model.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   
    /**
     * @param username Nombre de usuario a buscar.
     * @return Un objeto Optional que puede contener el usuario encontrado.
     */
    Optional<Usuario> findByUsername(String username);
}
