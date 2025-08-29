package org.example.factory;

import org.example.service.impl.ClienteServiceImpl;
import org.example.service.impl.CompraServiceImpl;
import org.example.service.impl.ProductoServiceImpl;
import org.example.service.impl.UsuarioServiceImpl;
import org.example.service.interfaces.IClienteService;
import org.example.service.interfaces.ICompraService;
import org.example.service.interfaces.IProductoService;
import org.example.service.interfaces.IUsuarioService;

public class ServiceFactory {
    public static IClienteService getClienteService() {
        return new ClienteServiceImpl(DAOFactory.getClienteDAO());
    }

    public static IUsuarioService getUsuarioService() {
        return new UsuarioServiceImpl(DAOFactory.getUsuarioDAO());
    }

    public static IProductoService getProductoService() {
        return new ProductoServiceImpl(DAOFactory.getProductooDAO());
    }

    public static ICompraService getCompraService() {
        // Si CompraService necesita múltiples DAOs, puedes inyectarlos aquí
        return new CompraServiceImpl(
                DAOFactory.geCompraDAO(),
                DAOFactory.getClienteDAO(),
                DAOFactory.getProductooDAO()
        );
    }
}
