package com.example.crudusuario.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * La clase Proyecto representa una entidad en la base de datos que almacena información 
 * sobre proyectos, incluyendo su estado, fecha de inicio y las tareas asociadas.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "proyectos") // Define el nombre de la tabla en la base de datos
public class Proyecto {

    @Id // Marca el campo "id" como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente
    private Long id; // Identificador único del proyecto

    private String nombre; // Nombre del proyecto
    
    private String descripcion; // Descripción del proyecto

    @Column(name = "fecha_inicio") // Define el nombre de la columna en la BD
    private LocalDate fechaInicio; // Fecha de inicio del proyecto

    @Enumerated(EnumType.STRING) // Define que el enum se almacenará como un string en la BD
    @Column(name = "estado", nullable = false) // Especifica el nombre de la columna y que no puede ser nulo
    private EstadoProyecto estado; // Estado del proyecto (Ej: ACTIVO, FINALIZADO, CANCELADO)

    /**
     * Relación Uno a Muchos con la entidad Tarea.
     * Un proyecto puede tener múltiples tareas asociadas.
     * 
     * - `mappedBy = "proyecto"`: Indica que la relación se mapea a través del atributo `proyecto` en la clase `Tarea`.
     * - `cascade = CascadeType.ALL`: Si eliminamos un proyecto, sus tareas asociadas también se eliminarán.
     * - `orphanRemoval = true`: Si una tarea se elimina de la lista, también se eliminará de la base de datos.
     */
    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas = new ArrayList<>();

    // Métodos Getters y Setters
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProyecto estado) {
        this.estado = estado;
    }

    public List<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(List<Tarea> tareas) {
        this.tareas = tareas;
    }
}
