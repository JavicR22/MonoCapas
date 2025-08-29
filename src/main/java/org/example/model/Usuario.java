package org.example.model;

/**
 * Modelo de dominio Usuario usando patrón Builder
 * Implementa principio Single Responsibility
 */
public class Usuario {
    private final Long id;
    private final String nombre;
    private final String email;
    private final String telefono;

    public Usuario(Long id, String nombre, String email, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
    }
    
    // Getters
    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public String getEmail() { return email; }
    public String getTelefono() { return telefono; }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nombre='%s', email='%s', telefono='%s'}",
                           id, nombre, email, telefono);
    }

}
