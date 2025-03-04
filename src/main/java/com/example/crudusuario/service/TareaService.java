package com.example.crudusuario.service;

import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.repository.TareaRepository;
import com.example.crudusuario.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class TareaService {

    private final TareaRepository tareaRepository;
    private final ProyectoRepository proyectoRepository;

    public TareaService(TareaRepository tareaRepository, ProyectoRepository proyectoRepository) {
        this.tareaRepository = tareaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    public List<Tarea> listarTareas() {
        return tareaRepository.findAll();
    }

    public Optional<Tarea> obtenerTareaPorId(Long id) {
        return tareaRepository.findById(id);
    }

    public Set<Tarea> obtenerTareasPorIds(List<Long> ids) {
        return new HashSet<>(tareaRepository.findAllById(ids));
    }

    public Tarea guardarTarea(Tarea tarea, List<Long> proyectosSeleccionados) {
        if (proyectosSeleccionados != null && !proyectosSeleccionados.isEmpty()) {
            Set<Proyecto> proyectos = new HashSet<>(proyectoRepository.findAllById(proyectosSeleccionados));
    
            // Añadimos la tarea a cada proyecto para mantener la relación bidireccional
            for (Proyecto proyecto : proyectos) {
                proyecto.getTareas().add(tarea);
            }
    
            tarea.setProyectos(proyectos);
        }
    
        return tareaRepository.save(tarea);
    }
    

    public Tarea actualizarTarea(Long id, Tarea tareaActualizada, List<Long> proyectosSeleccionados) {
        return tareaRepository.findById(id).map(tarea -> {
            tarea.setTitulo(tareaActualizada.getTitulo());
            tarea.setDescripcion(tareaActualizada.getDescripcion());
            tarea.setFechaLimite(tareaActualizada.getFechaLimite());
            tarea.setEstado(tareaActualizada.getEstado());
    
            if (proyectosSeleccionados != null) {
                Set<Proyecto> proyectos = new HashSet<>(proyectoRepository.findAllById(proyectosSeleccionados));
    
                // Primero eliminamos la tarea de los proyectos anteriores
                for (Proyecto proyecto : tarea.getProyectos()) {
                    proyecto.getTareas().remove(tarea);
                }
    
                // Ahora la añadimos a los nuevos proyectos
                for (Proyecto proyecto : proyectos) {
                    proyecto.getTareas().add(tarea);
                }
    
                tarea.setProyectos(proyectos);
            }
    
            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }
    
    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
