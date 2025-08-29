package org.example.controller;

import org.example.builders.ClienteBuilder;
import org.example.model.Cliente;
import org.example.model.Usuario;
import org.example.service.interfaces.IService;

import java.util.List;
import java.util.Optional;

public class ClienteController {
    private final IService clienteService;

    public ClienteController(IService clienteService) {
        this.clienteService = clienteService;
    }
    public String crearCliente(String nombre, String email) {
        try {
            Cliente cliente = new ClienteBuilder()
                    .setNombre(nombre)
                    .setEmail(email)
                    .build();

            clienteService.crear(cliente);
            return "Usuario creado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public List<Usuario> listarClientes() {
        return clienteService.obtenerTodos();
    }

    public Optional<Usuario> buscarClientes(Long id) {
        return clienteService.obtenerPorId(id);
    }

    public String actualizarClientes(Long id, String nombre, String email) {
        try {
            Cliente cliente = new ClienteBuilder()
                    .setId(id)
                    .setNombre(nombre)
                    .setEmail(email)
                    .build();

            clienteService.actualizar(cliente);
            return "Usuario actualizado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }

    public String eliminarCliente(Long id) {
        try {
            clienteService.eliminar(id);
            return "Usuario eliminado exitosamente";
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
