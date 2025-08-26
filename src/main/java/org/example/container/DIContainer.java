package org.example.container;

import org.example.dao.factory.ConnectionFactory;
import org.example.dao.impl.UsuarioDAOImpl;
import org.example.dao.interfaces.IConnectionFactory;
import org.example.dao.interfaces.IUsuarioDAO;
import org.example.service.impl.UsuarioServiceImpl;
import org.example.service.interfaces.IUsuarioService;
import java.util.HashMap;
import java.util.Map;

/**
 * Contenedor de inyección de dependencias simple
 * Implementa principio Dependency Inversion sin usar frameworks
 */
public class DIContainer {
    private static DIContainer instance;
    private final Map<Class<?>, Object> services = new HashMap<>();
    private String databaseType = "h2"; // Por defecto H2
    
    private DIContainer() {
        configurarServicios();
    }
    
    public static synchronized DIContainer getInstance() {
        if (instance == null) {
            instance = new DIContainer();
        }
        return instance;
    }
    
    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
        services.clear(); // Limpiar servicios para reconfigurar
        configurarServicios();
    }
    
    private void configurarServicios() {
        // Configurar factory de conexiones
        IConnectionFactory connectionFactory = ConnectionFactory.createFactory(databaseType);
        services.put(IConnectionFactory.class, connectionFactory);
        
        // Configurar DAO
        IUsuarioDAO usuarioDAO = new UsuarioDAOImpl(connectionFactory);
        services.put(IUsuarioDAO.class, usuarioDAO);
        
        // Configurar Service
        IUsuarioService usuarioService = new UsuarioServiceImpl(usuarioDAO);
        services.put(IUsuarioService.class, usuarioService);
    }
    
    @SuppressWarnings("unchecked")
    public <T> T getService(Class<T> serviceClass) {
        T service = (T) services.get(serviceClass);
        if (service == null) {
            throw new IllegalArgumentException("Servicio no encontrado: " + serviceClass.getName());
        }
        return service;
    }
    
    public String getDatabaseType() {
        return databaseType;
    }
}
