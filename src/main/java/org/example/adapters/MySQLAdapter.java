package org.example.adapters;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.example.config.DatabaseConfig;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLAdapter implements DataBaseAdapter {
    private final HikariDataSource dataSource;

    public MySQLAdapter(String url, String username, String password) {
        try {
            // Cargar el driver explícitamente
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Configurar HikariCP
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(url);
            config.setUsername(username);
            config.setPassword(password);

            // Configuraciones importantes para evitar conexiones cerradas
            config.setMinimumIdle(5);
            config.setMaximumPoolSize(20);
            config.setConnectionTimeout(30000); // 30 segundos
            config.setIdleTimeout(600000); // 10 minutos
            config.setMaxLifetime(1800000); // 30 minutos
            config.setLeakDetectionThreshold(60000); // 1 minuto

            // Configuraciones específicas de MySQL
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("useServerPrepStmts", "true");
            config.addDataSourceProperty("rewriteBatchedStatements", "true");
            config.addDataSourceProperty("cacheResultSetMetadata", "true");
            config.addDataSourceProperty("cacheServerConfiguration", "true");
            config.addDataSourceProperty("elideSetAutoCommits", "true");
            config.addDataSourceProperty("maintainTimeStats", "false");
            config.addDataSourceProperty("autoReconnect", "true");
            config.addDataSourceProperty("failOverReadOnly", "false");
            config.addDataSourceProperty("maxReconnects", "3");

            // Query de validación para verificar conexiones
            config.setConnectionTestQuery("SELECT 1");

            this.dataSource = new HikariDataSource(config);

            System.out.println("Pool de conexiones MySQL inicializado correctamente");

        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver MySQL no encontrado. Asegúrate de tener mysql-connector-java en el classpath", e);
        } catch (Exception e) {
            throw new RuntimeException("Error al inicializar el pool de conexiones MySQL", e);
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
                throw new SQLException("No se pudo obtener una conexión válida del pool");
            }
            return conn;
        } catch (SQLException e) {
            System.err.println("Error al obtener conexión MySQL: " + e.getMessage());
            throw e;
        }
    }

    public void close() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
            System.out.println("Pool de conexiones MySQL cerrado");
        }
    }

    // Método para verificar el estado del pool
    public void printPoolStats() {
        if (dataSource != null) {
            System.out.println("=== Estado del Pool MySQL ===");
            System.out.println("Conexiones activas: " + dataSource.getHikariPoolMXBean().getActiveConnections());
            System.out.println("Conexiones idle: " + dataSource.getHikariPoolMXBean().getIdleConnections());
            System.out.println("Total conexiones: " + dataSource.getHikariPoolMXBean().getTotalConnections());
            System.out.println("Threads esperando: " + dataSource.getHikariPoolMXBean().getThreadsAwaitingConnection());
        }
    }
}