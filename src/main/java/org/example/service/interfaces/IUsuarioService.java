package org.example.service.interfaces;

import org.example.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface para servicio de Usuario
 * Implementa principio Interface Segregation
 */
public interface IUsuarioService {
    void crearUsuario(Usuario usuario);
    Optional<Usuario> obtenerUsuarioPorId(Long id);
    List<Usuario> obtenerTodosLosUsuarios();
    void actualizarUsuario(Usuario usuario);
    void eliminarUsuario(Long id);
    boolean validarEmail(String email);
}
