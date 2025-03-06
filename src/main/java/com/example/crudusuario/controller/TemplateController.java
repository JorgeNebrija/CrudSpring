package com.example.crudusuario.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.crudusuario.model.Usuario;

@Controller 
public class TemplateController {

    
    @GetMapping("/login")
    public String login() {
        return "user/login"; // Devuelve la vista  en "user/login.html"
    }

    @GetMapping("/admin/dashboard")
    public String dashboard() {
        return "admin/dashboard"; // Devuelve la vista  en "admin/dashboard.html"
    }
}
