package org.example.builders;

import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;

import java.time.LocalDate;
import java.util.List;

public class CompraBuilder {
    private Long id;
    private Cliente cliente;
    private List<Producto> productos;
    private LocalDate fecha;
    private double total;

    public CompraBuilder setId(Long id) {
        this.id = id;
        return this;
    }

    public CompraBuilder setCliente(Cliente cliente) {
        this.cliente = cliente;
        return this;
    }

    public CompraBuilder setProductos(List<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public CompraBuilder setFecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public CompraBuilder setTotal(double total) {
        this.total = total;
        return this;
    }

    public Compra build() {
        if (cliente == null) {
            throw new IllegalArgumentException("La compra debe tener un cliente");
        }
        if (productos == null || productos.isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un producto");
        }
        return new Compra(id, cliente, productos, fecha, total);
    }

}
