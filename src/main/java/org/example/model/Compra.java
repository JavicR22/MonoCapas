package org.example.model;

import java.time.LocalDate;
import java.util.List;

public class Compra {
    private Long id;
    private Cliente cliente;
    private List<Producto> productos;
    private LocalDate fecha;
    private double total;

    public Compra(Long id, Cliente cliente, List<Producto> productos, LocalDate fecha, double total) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
        this.fecha = fecha;
        this.total = total;
    }
    // Getters y Setters
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public List<Producto> getProductos() { return productos; }
    public LocalDate getFecha() { return fecha; }
    public double getTotal() { return total; }

    public void setId(Long id) { this.id = id; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public void setTotal(double total) { this.total = total; }
}
