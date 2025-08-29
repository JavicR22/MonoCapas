package org.example.dao.impl;

import org.example.adapters.DataBaseAdapter;
import org.example.builders.UsuarioBuilder;
import org.example.dao.interfaces.IDAO;
import org.example.model.Usuario;
import java.sql.*;
import java.util.*;

/**
 * Implementación de DAO para Usuario
 * Implementa principios Single Responsibility y Dependency Inversion
 */
public class UsuarioDAOImpl implements IDAO<Usuario> {
    private final DataBaseAdapter dataBaseAdapter;




    public UsuarioDAOImpl (DataBaseAdapter dataBaseAdapter) {
        this.dataBaseAdapter = dataBaseAdapter;
    }
    

    
    @Override
    public void crear(Usuario usuario) {
        String sql = "INSERT INTO usuarios (nombre, email, telefono) VALUES (?, ?, ?)";
        
        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear usuario", e);
        }
    }
    
    @Override
    public Optional<Usuario> obtenerPorId(Long id) {
        String sql = "SELECT * FROM usuarios WHERE id = ?";
        
        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new UsuarioBuilder()
                    .setId(rs.getLong("id"))
                    .setNombre(rs.getString("nombre"))
                    .setEmail(rs.getString("email"))
                    .setTelefono(rs.getString("telefono"))
                    .build();
                return Optional.of(usuario);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener usuario por ID", e);
        }
        
        return Optional.empty();
    }
    
    @Override
    public List<Usuario> obtenerTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";
        
        try (Connection conn = dataBaseAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Usuario usuario = new UsuarioBuilder()
                    .setId(rs.getLong("id"))
                    .setNombre(rs.getString("nombre"))
                    .setEmail(rs.getString("email"))
                    .setTelefono(rs.getString("telefono"))
                    .build();
                usuarios.add(usuario);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los usuarios", e);
        }
        
        return usuarios;
    }
    
    @Override
    public void actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, email = ?, telefono = ? WHERE id = ?";
        
        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getTelefono());
            stmt.setLong(4, usuario.getId());
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar usuario", e);
        }
    }
    
    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar usuario", e);
        }
    }
}
