package org.example.dao.impl;

import org.example.adapters.DataBaseAdapter;
import org.example.dao.interfaces.IDAO;
import org.example.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductoDAOImpl implements IDAO<Producto> {
    private final DataBaseAdapter dataBaseAdapter;

    public ProductoDAOImpl(DataBaseAdapter dataBaseAdapter) {
        this.dataBaseAdapter = dataBaseAdapter;
    }

    @Override
    public void crear(Producto producto) {
        String sql = "INSERT INTO producto (nombre, precio) VALUES (?, ?)";

        try (Connection conn = dataBaseAdapter.getConnection();

             PreparedStatement stmt = conn.prepareStatement(sql)) {
            System.out.println(producto.getNombre());
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al crear producto", e);
        }
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        String sql = "SELECT * FROM producto WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Producto producto = new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                );
                return Optional.of(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener producto por ID", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM producto";

        try (Connection conn = dataBaseAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio")
                );
                productos.add(producto);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todos los productos", e);
        }

        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre = ?, precio = ? WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setLong(3, producto.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar producto", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sql = "DELETE FROM producto WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar producto", e);
        }
    }
}
