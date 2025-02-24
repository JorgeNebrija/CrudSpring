package com.example.crudusuario.model;

import jakarta.persistence.*; // Importamos las anotaciones de JPA para la persistencia de datos

/**
 * La clase Usuario representa la entidad de usuario en la base de datos.
 * Se mapea a la tabla "usuarios" utilizando JPA.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "usuarios") // Define el nombre de la tabla en la base de datos
public class Usuario {
    
    @Id // Marca el campo "id" como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID de forma automática
    private Long id; // Identificador único del usuario
    
    private String username; // Nombre de usuario
    private String password; // Contraseña del usuario
    private String role; // Rol del usuario 

    /**
     * Constructor vacío requerido por JPA.
     */
    public Usuario() {}

    // Métodos getters para acceder a los atributos
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // Métodos setters para modificar los atributos
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }
    @Override
    public String toString() {
        return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", role=" + role + "]";
    }
}
