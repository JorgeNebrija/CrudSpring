package com.example.crudusuario.service;

import com.example.crudusuario.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.crudusuario.model.Usuario;


@Service 
public class UsuarioService implements UserDetailsService {
    
    private final UsuarioRepository usuarioRepository; 
    private final PasswordEncoder passwordEncoder; // Codificador de contraseñas para seguridad

    /**
     * Constructor que inyecta el repositorio de usuarios y el codificador de contraseñas.
     * @param usuarioRepository Repositorio de usuarios.
     * @param passwordEncoder Codificador de contraseñas de Spring Security.
     */
    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Método requerido por UserDetailsService para cargar un usuario por su nombre de usuario.
     * Se utiliza en la autenticación con Spring Security.
     * 
     * @param username Nombre de usuario a buscar.
     * @return UserDetails con la información del usuario para autenticación.
     * @throws UsernameNotFoundException Si el usuario no se encuentra en la base de datos.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));
        
        System.out.println(usuario.toString());

        /*Retorna un objeto User de Spring Security con las credenciales del usuario
        Se usa la clase User de Spring Security, que implementa UserDetails. */

        return User.builder()
                .username(usuario.getUsername()) // Establece el username
                .password(usuario.getPassword()) // Usa la contraseña almacenada en la BD (debe estar encriptada)
                .roles(usuario.getRole()) // Asigna los roles del usuario
                .build();
    }

   
    public Usuario registrarUsuario(Usuario usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword())); // Encripta la contraseña antes de guardarla
        return usuarioRepository.save(usuario);
    }
}
