package com.example.crudusuario.model;

import jakarta.persistence.*; // Importamos las anotaciones de JPA para la persistencia de datos


@Entity // Indica que esta clase es una entidad JPA
@Table(name = "usuarios") 
public class Usuario {
    
    @Id // Marca el campo "id" como la clave primaria de la tabla
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID de forma automática
    private Long id; // Identificador único del usuario
    
    private String username; 
    private String password; 
    private String role; 
    
    public Usuario() {}

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
