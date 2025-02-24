package com.example.crudusuario.model;

import jakarta.persistence.*;
import java.time.LocalDate;

/**
 * La clase Tarea representa una entidad en la base de datos que almacena las tareas 
 * asociadas a un proyecto específico.
 */
@Entity // Indica que esta clase es una entidad JPA
@Table(name = "tareas") // Define el nombre de la tabla en la base de datos
public class Tarea {
    
    @Id // Marca el campo "id" como clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera el ID automáticamente en la base de datos
    private Long id; // Identificador único de la tarea
    
    private String titulo; // Título de la tarea
    
    private String descripcion; // Descripción detallada de la tarea
    
    @Column(name = "fecha_limite") // Define el nombre de la columna en la BD
    private LocalDate fechaLimite; // Fecha límite para completar la tarea
    
    @Enumerated(EnumType.STRING) // Indica que el enum se almacenará como un string en la BD
    private EstadoTarea estado; // Estado de la tarea (Ej: PENDIENTE, EN_PROGRESO, COMPLETADA)
    
    @ManyToOne // Relación muchas tareas -> un proyecto
    @JoinColumn(name = "proyecto_id", nullable = false) // Clave foránea que relaciona la tarea con un proyecto
    private Proyecto proyecto; // Proyecto al que pertenece la tarea

    // Métodos Getters y Setters

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(LocalDate fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public EstadoTarea getEstado() {
        return estado;
    }

    public void setEstado(EstadoTarea estado) {
        this.estado = estado;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }

    /**
     * Método toString para representar el objeto en formato de cadena.
     */
    @Override
    public String toString() {
        return "Tarea [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + 
               ", fechaLimite=" + fechaLimite + ", estado=" + estado + ", proyecto=" + proyecto.getNombre() + "]";
    }
}
