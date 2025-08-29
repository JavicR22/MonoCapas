package org.example.config;

import org.example.adapters.DataBaseAdapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseInitializer implements DatabaseInitializer{
    private static final DataBaseAdapter dataBaseAdapter=DatabaseConfig.getMySQLAdapter();



    public static void inicializar() {


        try (Connection conn = dataBaseAdapter.getConnection();
             Statement stmt = conn.createStatement()) {
            String sqlCliente = """
    CREATE TABLE IF NOT EXISTS cliente (
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        email VARCHAR(100) NOT NULL UNIQUE
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    """;

            String sqlProducto = """
    CREATE TABLE IF NOT EXISTS producto (
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        nombre VARCHAR(100) NOT NULL,
        precio DECIMAL(10,2) NOT NULL
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    """;

            String sqlCompra = """
    CREATE TABLE IF NOT EXISTS compra (
        id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
        cliente_id BIGINT NOT NULL,
        fecha DATE NOT NULL,
        total DECIMAL(12,2) NOT NULL,
        FOREIGN KEY (cliente_id) REFERENCES cliente(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    """;

            String sqlCompraProducto = """
    CREATE TABLE IF NOT EXISTS compra_producto (
        compra_id BIGINT NOT NULL,
        producto_id BIGINT NOT NULL,
        cantidad INT NOT NULL DEFAULT 1,
        PRIMARY KEY (compra_id, producto_id),
        FOREIGN KEY (compra_id) REFERENCES compra(id),
        FOREIGN KEY (producto_id) REFERENCES producto(id)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    """;
            stmt.execute(sqlCliente);
            stmt.execute(sqlProducto);
            stmt.execute(sqlCompra);
            stmt.execute(sqlCompraProducto);


            // Otros CREATE TABLE adaptados a H2
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar tablas en MySQL", e);
        }
    }
}
