package org.example.service.impl;

import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.UsuarioDAOImpl;
import org.example.dao.interfaces.IDAO;
import org.example.model.Cliente;
import org.example.model.Usuario;
import org.example.service.interfaces.IClienteService;
import org.example.service.interfaces.IService;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class ClienteServiceImpl implements IClienteService {
    private final IDAO<Cliente> clienteDAO;
    private final Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");

    public ClienteServiceImpl(IDAO<Cliente> clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public void crear(Cliente cliente) {
        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (!validarEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }

        clienteDAO.crear(cliente);
    }

    @Override
    public Optional<Cliente> obtenerPorId(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        return clienteDAO.obtenerPorId(id);
    }

    @Override
    public List<Cliente> obtenerTodos() {
        return clienteDAO.obtenerTodos();
    }

    @Override
    public void actualizar(Cliente cliente) {
        if (cliente.getId() == null || cliente.getId() <= 0) {
            throw new IllegalArgumentException("ID inválido para actualización");
        }

        if (cliente.getNombre() == null || cliente.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }

        if (!validarEmail(cliente.getEmail())) {
            throw new IllegalArgumentException("Email inválido");
        }

        clienteDAO.actualizar(cliente);
    }

    @Override
    public void eliminar(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID inválido");
        }

        clienteDAO.eliminar(id);
    }

    public boolean validarEmail(String email) {
        return email != null && emailPattern.matcher(email).matches();
    }
}
