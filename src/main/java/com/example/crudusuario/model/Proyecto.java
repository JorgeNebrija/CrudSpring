package com.example.crudusuario.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "proyectos")
public class Proyecto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String descripcion;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProyecto estado;

    @OneToMany(mappedBy = "proyecto", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Tarea> tareas;  //Un proyecto puede tener muchas tareas

    public Proyecto() {}

    public Proyecto(String nombre, String descripcion, LocalDate fechaInicio, EstadoProyecto estado) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.estado = estado;
    }

    public void setId(Long id) { this.id = id; }
    public Long getId() { return id; }


    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public String getDescripcion() { return descripcion; }

    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaInicio() { return fechaInicio; }

    public void setEstado(EstadoProyecto estado) { this.estado = estado; }
    public EstadoProyecto getEstado() { return estado; }

    public void setTareas(List<Tarea> tareas) { this.tareas = tareas; }
    public List<Tarea> getTareas() { return tareas; }

}
