package org.example.builders;

import org.example.model.Usuario;

public class UsuarioBuilder {

        private Long id;
        private String nombre;
        private String email;
        private String telefono;

        public UsuarioBuilder setId(Long id) {
            this.id = id;
            return this;
        }

        public UsuarioBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public UsuarioBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder setTelefono(String telefono) {
            this.telefono = telefono;
            return this;
        }

        public Usuario build() {
            return new Usuario(id, nombre, email, telefono);
        }

}
