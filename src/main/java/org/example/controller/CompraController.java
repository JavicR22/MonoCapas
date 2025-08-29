package org.example.controller;

import org.example.builders.CompraBuilder;
import org.example.builders.UsuarioBuilder;
import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;
import org.example.model.Usuario;
import org.example.service.impl.ClienteServiceImpl;
import org.example.service.impl.CompraServiceImpl;
import org.example.service.impl.ProductoServiceImpl;
import org.example.service.interfaces.ICompraService;
import org.example.service.interfaces.IService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraController {
    private final ICompraService compraService;


    public CompraController(ICompraService compraService) {
        this.compraService = compraService;

    }

    public String crearCompra(Long idCliente, List<Long> productoIds, LocalDate fecha) {


        try {
            compraService.crearCompras(idCliente, productoIds, fecha);
            return "Compra creada exitosamente.";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }


    public List<Compra> listarCompras() {
        return compraService.obtenerTodos();
    }

    public Optional<Compra> buscarCompraPorId(Long id) {
        return compraService.obtenerPorId(id);
    }

    public String actualizarCompras(Long id, Cliente cliente, List<Producto> productos, LocalDate fecha, double total) {
        try {
            Compra compra = new CompraBuilder()
                    .setId(id)
                    .setCliente(cliente)
                    .setProductos(productos)
                    .setFecha(fecha)
                    .setTotal(total)
                    .build();

            compraService.actualizar(compra);
            return "Usuario actualizado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String eliminarCompra(Long id) {
        try {
            compraService.eliminar(id);
            return "Usuario eliminado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
