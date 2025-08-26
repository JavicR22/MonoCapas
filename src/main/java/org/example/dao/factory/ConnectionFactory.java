package org.example.dao.factory;

import org.example.config.DatabaseConfig;
import org.example.dao.interfaces.IConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Factory Method para crear conexiones de base de datos
 * Implementa principios Open/Closed y Dependency Inversion
 */
public abstract class ConnectionFactory implements IConnectionFactory {
    
    public static IConnectionFactory createFactory(String type) {
        return switch (type.toLowerCase()) {
            case "mysql" -> new MySQLConnectionFactory();
            case "h2" -> new H2ConnectionFactory();
            default -> throw new IllegalArgumentException("Tipo de base de datos no soportado: " + type);
        };
    }
    
    // Clase interna para MySQL
    private static class MySQLConnectionFactory extends ConnectionFactory {
        @Override
        public Connection createConnection() {
            try {
                DatabaseConfig config = DatabaseConfig.getInstance();
                return DriverManager.getConnection(
                    config.getMysqlUrl(),
                    config.getMysqlUser(),
                    config.getMysqlPassword()
                );
            } catch (SQLException e) {
                throw new RuntimeException("Error al conectar con MySQL", e);
            }
        }
        
        @Override
        public String getDatabaseType() {
            return "MySQL";
        }
    }
    
    // Clase interna para H2
    private static class H2ConnectionFactory extends ConnectionFactory {
        @Override
        public Connection createConnection() {
            try {
                DatabaseConfig config = DatabaseConfig.getInstance();
                return DriverManager.getConnection(
                    config.getH2Url(),
                    config.getH2User(),
                    config.getH2Password()
                );
            } catch (SQLException e) {
                throw new RuntimeException("Error al conectar con H2", e);
            }
        }
        
        @Override
        public String getDatabaseType() {
            return "H2";
        }
    }
}
