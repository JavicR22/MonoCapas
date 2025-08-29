package org.example.config;

import org.example.adapters.DataBaseAdapter;
import org.example.adapters.H2Adapter;
import org.example.adapters.MySQLAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuración de base de datos usando patrón Singleton
 * Implementa principio Single Responsibility
 */
public class DatabaseConfig {

    private static Properties properties;
    private static MySQLAdapter mysqlAdapter;
    private static H2Adapter h2Adapter;

    static {
        loadProperties();
    }

    private static void loadProperties() {
        properties = new Properties();
        try (InputStream input = DatabaseConfig.class.getClassLoader()
                .getResourceAsStream("database.properties")) {

            if (input == null) {
                System.out.println("database.properties no encontrado, usando valores por defecto");
                // Valores por defecto
                properties.setProperty("mysql.url", "jdbc:mysql://localhost:3306/tienda_db?useSSL=false&serverTimezone=UTC&autoReconnect=true&useUnicode=true&characterEncoding=UTF-8");
                properties.setProperty("mysql.user", "root");
                properties.setProperty("mysql.password", "");
                properties.setProperty("h2.url", "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE");
                properties.setProperty("h2.user", "sa");
                properties.setProperty("h2.password", "");
            } else {
                properties.load(input);
                System.out.println("Propiedades de base de datos cargadas desde archivo");
            }

        } catch (IOException e) {
            System.err.println("Error al cargar propiedades: " + e.getMessage());
            throw new RuntimeException("Error al cargar propiedades de base de datos", e);
        }
    }

    public static synchronized DataBaseAdapter getMySQLAdapter() {
        if (mysqlAdapter == null) {
            String mysqlUrl = properties.getProperty("mysql.url");
            String mysqlUsername = properties.getProperty("mysql.user");
            String mysqlPassword = properties.getProperty("mysql.password");

            System.out.println("Conectando a MySQL: " + mysqlUrl);
            mysqlAdapter = new MySQLAdapter(mysqlUrl, mysqlUsername, mysqlPassword);
        }
        return mysqlAdapter;
    }

    public static synchronized DataBaseAdapter getH2Adapter() {
        if (h2Adapter == null) {
            String h2Url = properties.getProperty("h2.url");
            String h2Username = properties.getProperty("h2.user");
            String h2Password = properties.getProperty("h2.password");

            System.out.println("Conectando a H2: " + h2Url);
            h2Adapter = new H2Adapter(h2Url, h2Username, h2Password);
        }
        return h2Adapter;
    }
    // Método para cerrar todos los pools al finalizar la aplicación
    public static synchronized void closeAllConnections() {
        if (mysqlAdapter != null) {
            mysqlAdapter.close();
            mysqlAdapter = null;
        }
        if (h2Adapter != null) {
            h2Adapter.close();
            h2Adapter = null;
        }
        System.out.println("Todas las conexiones cerradas");
    }

    public static void printAllPoolStats() {
        if (mysqlAdapter != null) {
            mysqlAdapter.printPoolStats();
        }
    }
}