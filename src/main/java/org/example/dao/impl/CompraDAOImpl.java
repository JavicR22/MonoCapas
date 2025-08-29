package org.example.dao.impl;

import org.example.adapters.DataBaseAdapter;
import org.example.dao.interfaces.IDAO;
import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraDAOImpl implements IDAO<Compra> {
    private final DataBaseAdapter dataBaseAdapter;

    public CompraDAOImpl(DataBaseAdapter dataBaseAdapter) {
        this.dataBaseAdapter = dataBaseAdapter;
    }

    @Override
    public void crear(Compra compra) {
        String sqlCompra = "INSERT INTO compras (cliente_id, fecha, total) VALUES (?, ?, ?)";
        String sqlProductos = "INSERT INTO compra_productos (compra_id, producto_id) VALUES (?, ?)";

        try (Connection conn = dataBaseAdapter.getConnection()) {
            conn.setAutoCommit(false);

            // Insertar la compra
            try (PreparedStatement stmt = conn.prepareStatement(sqlCompra, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, compra.getCliente().getId());
                stmt.setDate(2, Date.valueOf(compra.getFecha()));
                stmt.setDouble(3, compra.getTotal());
                stmt.executeUpdate();

                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    compra.setId(rs.getLong(1));
                }
            }

            // Insertar productos asociados
            try (PreparedStatement stmt = conn.prepareStatement(sqlProductos)) {
                for (Producto producto : compra.getProductos()) {
                    stmt.setLong(1, compra.getId());
                    stmt.setLong(2, producto.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al crear compra", e);
        }
    }

    @Override
    public Optional<Compra> obtenerPorId(Long id) {
        String sqlCompra = "SELECT * FROM compras WHERE id = ?";
        String sqlProductos = "SELECT p.id, p.nombre, p.precio " +
                "FROM productos p JOIN compra_productos cp ON p.id = cp.producto_id " +
                "WHERE cp.compra_id = ?";

        try (Connection conn = dataBaseAdapter.getConnection()) {

            Compra compra = null;

            try (PreparedStatement stmt = conn.prepareStatement(sqlCompra)) {
                stmt.setLong(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    compra = new Compra(
                            rs.getLong("id"),
                            new Cliente(rs.getLong("cliente_id"), null, null), // cliente cargado en otra capa si quieres
                            new ArrayList<>(),
                            rs.getDate("fecha").toLocalDate(),
                            rs.getDouble("total")
                    );
                }
            }

            if (compra != null) {
                try (PreparedStatement stmt = conn.prepareStatement(sqlProductos)) {
                    stmt.setLong(1, id);
                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        Producto producto = new Producto(
                                rs.getLong("id"),
                                rs.getString("nombre"),
                                rs.getDouble("precio")
                        );
                        compra.getProductos().add(producto);
                    }
                }
                return Optional.of(compra);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener compra por ID", e);
        }

        return Optional.empty();
    }

    @Override
    public List<Compra> obtenerTodos() {
        List<Compra> compras = new ArrayList<>();
        String sql = "SELECT * FROM compras";

        try (Connection conn = dataBaseAdapter.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Compra compra = new Compra(
                        rs.getLong("id"),
                        new Cliente(rs.getLong("cliente_id"), null, null),
                        new ArrayList<>(),
                        rs.getDate("fecha").toLocalDate(),
                        rs.getDouble("total")
                );
                compras.add(compra);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener todas las compras", e);
        }

        return compras;
    }

    @Override
    public void actualizar(Compra compra) {
        String sqlCompra = "UPDATE compras SET cliente_id = ?, fecha = ?, total = ? WHERE id = ?";
        String sqlDeleteProductos = "DELETE FROM compra_productos WHERE compra_id = ?";
        String sqlInsertProductos = "INSERT INTO compra_productos (compra_id, producto_id) VALUES (?, ?)";

        try (Connection conn = dataBaseAdapter.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlCompra)) {
                stmt.setLong(1, compra.getCliente().getId());
                stmt.setDate(2, Date.valueOf(compra.getFecha()));
                stmt.setDouble(3, compra.getTotal());
                stmt.setLong(4, compra.getId());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteProductos)) {
                stmt.setLong(1, compra.getId());
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlInsertProductos)) {
                for (Producto producto : compra.getProductos()) {
                    stmt.setLong(1, compra.getId());
                    stmt.setLong(2, producto.getId());
                    stmt.addBatch();
                }
                stmt.executeBatch();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar compra", e);
        }
    }

    @Override
    public void eliminar(Long id) {
        String sqlDeleteProductos = "DELETE FROM compra_productos WHERE compra_id = ?";
        String sqlDeleteCompra = "DELETE FROM compras WHERE id = ?";

        try (Connection conn = dataBaseAdapter.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteProductos)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }

            try (PreparedStatement stmt = conn.prepareStatement(sqlDeleteCompra)) {
                stmt.setLong(1, id);
                stmt.executeUpdate();
            }

            conn.commit();
        } catch (SQLException e) {
            throw new RuntimeException("Error al eliminar compra", e);
        }
    }
}
