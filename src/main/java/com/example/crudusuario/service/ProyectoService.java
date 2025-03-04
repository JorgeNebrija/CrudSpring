package com.example.crudusuario.service;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.repository.ProyectoRepository;
import com.example.crudusuario.repository.TareaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.HashSet;

@Service
public class ProyectoService {
    
    private final ProyectoRepository proyectoRepository;
    private final TareaRepository tareaRepository;

    public ProyectoService(ProyectoRepository proyectoRepository, TareaRepository tareaRepository) {
        this.proyectoRepository = proyectoRepository;
        this.tareaRepository = tareaRepository;
    }

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public Set<Proyecto> obtenerProyectosPorIds(List<Long> ids) {
        return new HashSet<>(proyectoRepository.findAllById(ids));
    }

    public Proyecto guardarProyecto(Proyecto proyecto, List<Long> tareasSeleccionadas) {
        if (tareasSeleccionadas != null && !tareasSeleccionadas.isEmpty()) {
            Set<Tarea> tareas = new HashSet<>(tareaRepository.findAllById(tareasSeleccionadas));
            proyecto.setTareas(tareas);
        }
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizarProyecto(Long id, Proyecto proyectoActualizado, List<Long> tareasSeleccionadas) {
        return proyectoRepository.findById(id).map(proyecto -> {
            proyecto.setNombre(proyectoActualizado.getNombre());
            proyecto.setDescripcion(proyectoActualizado.getDescripcion());
            proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
            proyecto.setEstado(proyectoActualizado.getEstado());

            if (tareasSeleccionadas != null) {
                Set<Tarea> tareas = new HashSet<>(tareaRepository.findAllById(tareasSeleccionadas));
                proyecto.setTareas(tareas);
            }

            return proyectoRepository.save(proyecto);
        }).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
}
