package org.example.factory;

import org.example.controller.ClienteController;
import org.example.controller.CompraController;
import org.example.controller.ProductoController;
import org.example.controller.UsuarioController;
import org.example.service.interfaces.*;

public class ControllerFactory {




    public static UsuarioController crearUsuarioController() {
        return new UsuarioController(ServiceFactory.getUsuarioService());
    }

    public static CompraController crearCompraController() {
        return new CompraController(ServiceFactory.getCompraService());
    }

    public static ClienteController crearClienteController() {
        return new ClienteController(ServiceFactory.getClienteService());
    }

    public static ProductoController crearProductoController() {
        return new ProductoController(ServiceFactory.getProductoService());
    }

}
