package org.example.controller;

import org.example.builders.UsuarioBuilder;
import org.example.model.Usuario;
import org.example.service.interfaces.IService;

import java.util.List;
import java.util.Optional;

public class UsuarioController {
    private final IService usuarioService;

    public UsuarioController(IService usuarioService) {
        this.usuarioService = usuarioService;
    }

    public String crearUsuario(String nombre, String email, String telefono) {
        try {
            Usuario usuario = new UsuarioBuilder()
                    .setNombre(nombre)
                    .setEmail(email)
                    .setTelefono(telefono)
                    .build();

            usuarioService.crear(usuario);
            return "Usuario creado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<Usuario> listarUsuarios() {
        return usuarioService.obtenerTodos();
    }

    public Optional<Usuario> buscarUsuario(Long id) {
        return usuarioService.obtenerPorId(id);
    }

    public String actualizarUsuario(Long id, String nombre, String email, String telefono) {
        try {
            Usuario usuario = new UsuarioBuilder()
                    .setId(id)
                    .setNombre(nombre)
                    .setEmail(email)
                    .setTelefono(telefono)
                    .build();

            usuarioService.actualizar(usuario);
            return "Usuario actualizado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String eliminarUsuario(Long id) {
        try {
            usuarioService.eliminar(id);
            return "Usuario eliminado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
