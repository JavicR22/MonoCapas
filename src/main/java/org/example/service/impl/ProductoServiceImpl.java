package org.example.service.impl;

import org.example.dao.interfaces.IDAO;
import org.example.model.Producto;
import org.example.service.interfaces.IProductoService;
import org.example.service.interfaces.IService;

import java.util.List;
import java.util.Optional;

public class ProductoServiceImpl implements IProductoService {
    private final IDAO<Producto> productoDAO;

    // Inyección por constructor (puede ser vía fábrica o contenedor DI)
    public ProductoServiceImpl(IDAO<Producto> productoDAO) {
        this.productoDAO = productoDAO;
    }

    @Override
    public void crear(Producto producto) {
        if (producto == null) {
            throw new IllegalArgumentException("El producto no puede ser nulo");
        }
        productoDAO.crear(producto);
    }

    @Override
    public Optional<Producto> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para búsqueda");
        }
        return productoDAO.obtenerPorId(id);
    }

    @Override
    public List<Producto> obtenerTodos() {
        return productoDAO.obtenerTodos();
    }

    @Override
    public void actualizar(Producto producto) {
        if (producto == null || producto.getId() == null || producto.getId() <= 0) {
            throw new IllegalArgumentException("Producto inválido para actualización");
        }
        productoDAO.actualizar(producto);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido para eliminación");
        }
        productoDAO.eliminar(id);
    }
}
