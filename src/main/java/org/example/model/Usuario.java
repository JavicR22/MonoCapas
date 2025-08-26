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
    
    private Usuario(Builder builder) {
        this.id = builder.id;
        this.nombre = builder.nombre;
        this.email = builder.email;
        this.telefono = builder.telefono;
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
    
    // Patrón Builder
    public static class Builder {
        private Long id;
        private String nombre;
        private String email;
        private String telefono;
        
        public Builder setId(Long id) {
            this.id = id;
            return this;
        }
        
        public Builder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }
        
        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }
        
        public Builder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        
        public Usuario build() {
            return new Usuario(this);
        }
    }
}
