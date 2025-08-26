package org.example.config;

/**
 * Configuración de base de datos usando patrón Singleton
 * Implementa principio Single Responsibility
 */
public class DatabaseConfig {
    private static DatabaseConfig instance;
    
    // Configuración MySQL
    private final String mysqlUrl = "jdbc:mysql://localhost:3306/monocapas";
    private final String mysqlUser = "root";
    private final String mysqlPassword = "20021130";
    
    // Configuración H2
    private final String h2Url = "jdbc:h2:~/tabla";
    private final String h2User = "sa";
    private final String h2Password = "";
    
    private DatabaseConfig() {}
    
    public static synchronized DatabaseConfig getInstance() {
        if (instance == null) {
            instance = new DatabaseConfig();
        }
        return instance;
    }
    
    public String getMysqlUrl() { return mysqlUrl; }
    public String getMysqlUser() { return mysqlUser; }
    public String getMysqlPassword() { return mysqlPassword; }
    public String getH2Url() { return h2Url; }
    public String getH2User() { return h2User; }
    public String getH2Password() { return h2Password; }
}
