package com.example.crudusuario.service;

import com.example.crudusuario.model.Proyecto;
import com.example.crudusuario.repository.ProyectoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProyectoService {
    private final ProyectoRepository proyectoRepository;

    public ProyectoService(ProyectoRepository proyectoRepository) {
        this.proyectoRepository = proyectoRepository;
    }

    public List<Proyecto> listarProyectos() {
        return proyectoRepository.findAll();
    }

    public Optional<Proyecto> obtenerProyectoPorId(Long id) {
        return proyectoRepository.findById(id);
    }

    public Proyecto guardarProyecto(Proyecto proyecto) {
        return proyectoRepository.save(proyecto);
    }

    public Proyecto actualizarProyecto(Long id, Proyecto proyectoActualizado) {
        return proyectoRepository.findById(id).map(proyecto -> {
            proyecto.setNombre(proyectoActualizado.getNombre());
            proyecto.setDescripcion(proyectoActualizado.getDescripcion());
            proyecto.setFechaInicio(proyectoActualizado.getFechaInicio());
            proyecto.setEstado(proyectoActualizado.getEstado());
            proyecto.setTareas(proyectoActualizado.getTareas());
            return proyectoRepository.save(proyecto);
        }).orElseThrow(() -> new RuntimeException("Proyecto no encontrado"));
    }

    public void eliminarProyecto(Long id) {
        proyectoRepository.deleteById(id);
    }
}
