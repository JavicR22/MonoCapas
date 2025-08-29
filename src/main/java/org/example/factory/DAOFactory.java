package org.example.factory;

import org.example.config.DatabaseConfig;
import org.example.dao.impl.ClienteDAOImpl;
import org.example.dao.impl.CompraDAOImpl;
import org.example.dao.impl.ProductoDAOImpl;
import org.example.dao.impl.UsuarioDAOImpl;
import org.example.dao.interfaces.IDAO;
import org.example.model.Cliente;
import org.example.model.Compra;
import org.example.model.Producto;
import org.example.model.Usuario;

public class DAOFactory {
    public static IDAO<Cliente> getClienteDAO() {
        // Cliente usa MySQL
        return new ClienteDAOImpl(DatabaseConfig.getMySQLAdapter());
    }

    public static IDAO<Usuario> getUsuarioDAO() {
        // Usuario usa H2
        return new UsuarioDAOImpl(DatabaseConfig.getH2Adapter());
    }
    public static IDAO<Producto> getProductooDAO() {
        // Usuario usa H2
        return new ProductoDAOImpl(DatabaseConfig.getMySQLAdapter());
    }
    public static IDAO<Compra> geCompraDAO() {
        // Usuario usa H2
        return new CompraDAOImpl(DatabaseConfig.getMySQLAdapter());
    }
}
