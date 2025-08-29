package org.example.service.impl;

import org.example.builders.CompraBuilder;
import org.example.dao.interfaces.IDAO;
import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;
import org.example.service.interfaces.ICompraService;
import org.example.service.interfaces.IService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CompraServiceImpl implements ICompraService {
    private final IDAO<Compra> compraDAO;
    private final IDAO<Producto> productoService;
    private final IDAO<Cliente> clienteService;


    // Inyección por constructor
    public CompraServiceImpl(IDAO<Compra> compraDAO, IDAO<Cliente> clienteService,
                             IDAO<Producto> productoService) {
        this.clienteService=clienteService;
        this.productoService=productoService;
        this.compraDAO = compraDAO;
    }

    @Override
    public void crear(Compra compra) {
        if (compra == null) {
            throw new IllegalArgumentException("La compra no puede ser nula");
        }
        if (compra.getCliente() == null) {
            throw new IllegalArgumentException("La compra debe tener un cliente");
        }
        if (compra.getProductos() == null || compra.getProductos().isEmpty()) {
            throw new IllegalArgumentException("La compra debe tener al menos un producto");
        }
        compraDAO.crear(compra);
    }

    @Override
    public Optional<Compra> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para búsqueda");
        }
        return compraDAO.obtenerPorId(id);
    }

    @Override
    public List<Compra> obtenerTodos() {
        return compraDAO.obtenerTodos();
    }

    @Override
    public void actualizar(Compra compra) {
        if (compra == null || compra.getId() <= 0) {
            throw new IllegalArgumentException("Compra inválida para actualización");
        }
        compraDAO.actualizar(compra);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para eliminación");
        }
        compraDAO.eliminar(id);
    }
    @Override
    public void crearCompras(Long clienteId, List<Long> productoIds, LocalDate fecha) {
        Cliente cliente = clienteService.obtenerPorId(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con ID: " + clienteId));

        List<Producto> productos = new ArrayList<>();
        double total = 0;

        for (Long productoId : productoIds) {
            Producto producto = productoService.obtenerPorId(productoId)
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado con ID: " + productoId));
            productos.add(producto);
            total += producto.getPrecio();
        }

        Compra compra = new CompraBuilder()
                .setCliente(cliente)
                .setProductos(productos)
                .setFecha(fecha)
                .setTotal(total)
                .build();

        compraDAO.crear(compra); 
    }

}
