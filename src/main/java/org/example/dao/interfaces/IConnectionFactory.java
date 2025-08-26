package org.example.dao.interfaces;

import java.sql.Connection;

/**
 * Interface para Factory de conexiones
 * Implementa principio Interface Segregation
 */
public interface IConnectionFactory {
    Connection createConnection();
    String getDatabaseType();
}
