package org.example.config;

import org.example.adapters.DataBaseAdapter;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class H2DatabaseInitializar implements DatabaseInitializer{
    private static final DataBaseAdapter adapter=DatabaseConfig.getH2Adapter();



    public static void inicializar() {
        try (Connection conn = adapter.getConnection();
             Statement stmt = conn.createStatement()) {

            String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    nombre VARCHAR(100) NOT NULL,
                    email VARCHAR(100) NOT NULL UNIQUE,
                    telefono VARCHAR(20)
                )
                """;
            stmt.execute(sqlUsuarios);

            // Otros CREATE TABLE adaptados a H2
        } catch (SQLException e) {
            throw new RuntimeException("Error al inicializar tablas en H2", e);
        }
    }
}
