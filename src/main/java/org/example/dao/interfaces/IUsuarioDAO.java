package org.example.dao.interfaces;

import org.example.model.Usuario;
import java.util.List;
import java.util.Optional;

/**
 * Interface para DAO de Usuario
 * Implementa principio Interface Segregation
 */
public interface IUsuarioDAO {
    void crear(Usuario usuario);
    Optional<Usuario> obtenerPorId(Long id);
    List<Usuario> obtenerTodos();
    void actualizar(Usuario usuario);
    void eliminar(Long id);
}
