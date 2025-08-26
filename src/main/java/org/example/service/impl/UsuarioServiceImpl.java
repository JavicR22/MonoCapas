package org.example.service.impl;

import org.example.dao.interfaces.IUsuarioDAO;
import org.example.model.Usuario;
import org.example.service.interfaces.IUsuarioService;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * Implementación del servicio de Usuario
 * Implementa principios Single Responsibility y Dependency Inversion
 */
public class UsuarioServiceImpl implements IUsuarioService {
    private final IUsuarioDAO usuarioDAO;
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    public UsuarioServiceImpl(IUsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }
    
    @Override
    public void crearUsuario(Usuario usuario) {
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (!validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        usuarioDAO.crear(usuario);
    }
    
    @Override
    public Optional<Usuario> obtenerUsuarioPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        return usuarioDAO.obtenerPorId(id);
    }
    
    @Override
    public List<Usuario> obtenerTodosLosUsuarios() {
        return usuarioDAO.obtenerTodos();
    }
    
    @Override
    public void actualizarUsuario(Usuario usuario) {
        if (usuario.getId() == null || usuario.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido para actualización");
        }
        
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (!validarEmail(usuario.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }
        
        usuarioDAO.actualizar(usuario);
    }
    
    @Override
    public void eliminarUsuario(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }
        
        usuarioDAO.eliminar(id);
    }
    
    @Override
    public boolean validarEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }
}
