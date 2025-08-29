package org.example.dao.impl;

import org.example.adapters.DataBaseAdapter;
import org.example.dao.interfaces.IDAO;
import org.example.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClienteDAOImpl implements IDAO<Cliente> {
    private final DataBaseAdapter dataBaseAdapter;

    public ClienteDAOImpl(DataBaseAdapter dataBaseAdapter) {
        this.dataBaseAdapter = dataBaseAdapter;
    }

    @Override
    public void crear(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, email) VALUES (?, ?)";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear cliente", e);
        }
    }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) {
        String sql = "SELECT * FROM clientes WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
                return Optional.of(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener cliente por ID", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = dataBaseAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("email")
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los clientes", e);
        }

        return clientes;
    }

    @Override
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, email = ? WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setLong(3, cliente.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar cliente", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar cliente", e);
        }
    }
}
