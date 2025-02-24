package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.crudusuario.model.Usuario;

/**
 * Controlador que maneja las vistas de login y del panel de administración.
 */
@Controller // Indica que esta clase es un controlador de Spring MVC
public class TemplateController {

    /**
     * Muestra la vista de login.
     * 
     * @return La vista "user/login".
     */
    @GetMapping("/login")
    public String login() {
        return "user/login"; // Devuelve la vista de login ubicada en "user/login.html"
    }

    /**
     * Muestra el panel de administración.
     * 
     * @return La vista "admin/dashboard".
     */
    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // Devuelve la vista del dashboard ubicada en "admin/dashboard.html"
    }
}
