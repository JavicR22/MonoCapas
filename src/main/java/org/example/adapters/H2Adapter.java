package org.example.adapters;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Adapter implements DataBaseAdapter {
    private final HikariDataSource dataSource;

    public H2Adapter(String url, String username, String password) {
        try {
            // Cargar el driver explícitamente
            Class.forName("org.h2.Driver");

            // Configurar HikariCP para H2
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password != null ? password : "");

            // Configuraciones para H2
            config.setMinimumIdle(2);
            config.setMaximumPoolSize(10);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(300000); // 5 minutos
            config.setMaxLifetime(900000); // 15 minutos

            // Query de validación para H2
            config.setConnectionTestQuery("SELECT 1");

            this.dataSource = new HikariDataSource(config);

            System.out.println("Pool de conexiones H2 inicializado correctamente");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver H2 no encontrado. Asegúrate de tener h2 en el classpath", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el pool de conexiones H2", e);
        }
    }

    @Override
    public String getDatabaseType() {
        return null;
    }

    @Override
    public void closeConnection() {

    }

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Connection conn = dataSource.getConnection();
            if (conn == null || conn.isClosed()) {
                throw new SQLException("No se pudo obtener una conexión válida del pool H2");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión H2: " + e.getMessage());
            throw e;
        }
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexiones H2 cerrado");
        }
    }
}