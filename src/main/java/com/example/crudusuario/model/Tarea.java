package com.example.crudusuario.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "tareas")
public class Tarea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descripcion;

    @Column(name = "fecha_limite")
    private LocalDate fechaLimite;

    @Enumerated(EnumType.STRING)
    private EstadoTarea estado;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false) 
    private Proyecto proyecto;

    public Tarea() {}

    public Tarea(String titulo, String descripcion, LocalDate fechaLimite, EstadoTarea estado, Proyecto proyecto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaLimite = fechaLimite;
        this.estado = estado;
        this.proyecto = proyecto;
    }
    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }

    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getTitulo() { return titulo; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDescripcion() { return descripcion; }

    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
    public LocalDate getFechaLimite() { return fechaLimite; }

    public void setEstado(EstadoTarea estado) { this.estado = estado; }
    public EstadoTarea getEstado() { return estado; }

    public void setProyecto(Proyecto proyecto) { this.proyecto = proyecto; }
    public Proyecto getProyecto() { return proyecto; }
}
