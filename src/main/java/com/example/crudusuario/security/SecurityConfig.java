package com.example.crudusuario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import java.io.IOException;

/**
 * Define las reglas de autenticación, autorización y cifrado de contraseñas.
 */
@Configuration // Indica que esta clase proporciona configuración a la aplicación
class SecurityConfig {

    /**
     * Define qué rutas son públicas y cuáles requieren autenticación.
     *
     * @param http Configuración de seguridad de Spring.
     * @return Filtro de seguridad configurado.
     * @throws Exception En caso de error en la configuración.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable()) // Desactiva CSRF para facilitar pruebas
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/css/**", "/js/**").permitAll()

                .requestMatchers("/proyectos/**").hasAnyRole("USER", "ADMIN") 
                .requestMatchers("/tareas/**").hasAnyRole("USER", "ADMIN") 

                .requestMatchers("/admin/**").hasAnyRole("ADMIN") 

                // Cualquier otra ruta requiere autenticación
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login") 
                .successHandler(customSuccessHandler()) // Redirige según el rol tras autenticarse
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout") 
                .logoutSuccessUrl("/login?logout") 
                .permitAll());

        return http.build();
    }

    /**
     * Manejador de autenticación personalizada.
     * Redirige a diferentes páginas según el rol del usuario tras iniciar sesión.
     *
     * @return AuthenticationSuccessHandler personalizado.
     */
    @Bean
    public AuthenticationSuccessHandler customSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException {

                if (authentication.getAuthorities().stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN"))) {
                    response.sendRedirect("/admin/dashboard");
                } else {
                    response.sendRedirect("/home");
                }
            }
        };
    }

    /**
     *
     * @param authConfig Configuración de autenticación.
     * @return AuthenticationManager configurado.
     * @throws Exception En caso de error en la configuración.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * Bean para codificar contraseñas con BCrypt.
     * Se usa en el registro y autenticación de usuarios.
     *
     * @return Instancia de PasswordEncoder con BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

/**
 * Un `@Bean` es un objeto gestionado por el contenedor de Spring, 
 * definido explícitamente dentro de un método en una clase `@Configuration`.
 * Se utiliza para la inyección de dependencias, permitiendo que Spring 
 * cree y administre instancias de manera automática dentro de la aplicación.
 */
