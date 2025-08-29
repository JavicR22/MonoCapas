package org.example.builders;

import org.example.model.Cliente;
import org.example.model.Usuario;

public class ClienteBuilder {
    private Long id;
    private String nombre;
    private String email;


    public ClienteBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ClienteBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ClienteBuilder setEmail(String email) {
        this.email = email;
        return this;
    }


    public Cliente build() {
        return new Cliente(id, nombre, email);
    }
}
