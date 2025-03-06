package com.example.crudusuario.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.crudusuario.model.Usuario;
import com.example.crudusuario.service.UsuarioService;


@Controller 
public class UsuarioController {

    private final UsuarioService usuarioService; 

    
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    /** 
     * @param model Objeto Model para enviar datos a la vista.
     * @return La vista "user/registro".
     */
    @GetMapping("/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("usuario", new Usuario()); // Se añade un objeto vacío para el formulario
        return "user/registro"; 
    }

    /**
     * 
     * @param usuario Objeto Usuario con los datos enviados desde el formulario.
     * @return Redirige a la página de login tras el registro exitoso.
     */
    @PostMapping("/registro")
    public String registrarUsuario(@ModelAttribute Usuario usuario) {
        usuarioService.registrarUsuario(usuario); 
        return "redirect:/login"; 
    }

    /**
     * Redirige a la página de inicio del usuario según su rol.
     * 
     * @param model Objeto Model para enviar datos a la vista.
     * @return Redirección a "/admin/dashboard" si es ADMIN, o a "user/home" si es un usuario normal.
     */
    @GetMapping("/home")
    public String home(Model model) {
        // Obtiene la autenticación del usuario actual
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    
        if (auth.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin/dashboard"; 
        } else {
            return "user/home"; 
        }
    }
}
