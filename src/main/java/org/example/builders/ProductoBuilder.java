package org.example.builders;

import org.example.model.Producto;

public class ProductoBuilder {
    private Long id;
    private String nombre;
    private double precio;

    public ProductoBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public ProductoBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public ProductoBuilder setPrecio(double precio) {
        this.precio = precio;
        return this;
    }

    public Producto build() {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El producto debe tener un nombre");
        }
        if (precio <= 0) {
            throw new IllegalArgumentException("El producto debe tener un precio mayor a 0");
        }
        return new Producto(id, nombre, precio);
    }
}
