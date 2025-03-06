package com.example.crudusuario.service;

import com.example.crudusuario.model.Tarea;
import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.repository.TareaRepository;
import com.example.crudusuario.repository.ProyectoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

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

    public Tarea guardarTarea(Tarea tarea, Long proyectoId) {
        Proyecto proyecto = proyectoRepository.findById(proyectoId)
                .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
        
        tarea.setProyecto(proyecto);  //Cada tarea se asigna a un solo proyecto
        return tareaRepository.save(tarea);
    }

    public Tarea actualizarTarea(Long id, Tarea tareaActualizada, Long proyectoId) {
        return tareaRepository.findById(id).map(tarea -> {
            tarea.setTitulo(tareaActualizada.getTitulo());
            tarea.setDescripcion(tareaActualizada.getDescripcion());
            tarea.setFechaLimite(tareaActualizada.getFechaLimite());
            tarea.setEstado(tareaActualizada.getEstado());

            Proyecto proyecto = proyectoRepository.findById(proyectoId)
                    .orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));

            tarea.setProyecto(proyecto); //Se reasigna la tarea a un nuevo proyecto
            return tareaRepository.save(tarea);
        }).orElseThrow(() -> new RuntimeException("Tarea no encontrada"));
    }

    public void eliminarTarea(Long id) {
        tareaRepository.deleteById(id);
    }
}
