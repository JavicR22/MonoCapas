package org.example.controller;

import org.example.builders.CompraBuilder;
import org.example.builders.ProductoBuilder;
import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;
import org.example.model.Usuario;
import org.example.service.interfaces.IService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ProductoController {
    private final IService productoService;

    public ProductoController(IService productoService) {
        this.productoService = productoService;
    }
    public String crearProducto(String nombre, double precio) {
        try {
            Producto producto = new ProductoBuilder()
                    .setNombre(nombre)
                    .setPrecio(precio)
                    .build();

            productoService.crear(producto);
            return "Producto1 creado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<Producto> listarProductos() {
        return productoService.obtenerTodos();
    }

    public Optional<Producto> buscarProductoPorId(Long id) {
        return productoService.obtenerPorId(id);
    }

    public String actualizarProducto(Long id, String nombre, double precio) {
        try {
            Producto producto = new ProductoBuilder()
                    .setId(id)
                    .setNombre(nombre)
                    .setPrecio(precio)
                    .build();

            productoService.actualizar(producto);
            return "Usuario actualizado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String eliminarProducto(Long id) {
        try {
            productoService.eliminar(id);
            return "Usuario eliminado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
