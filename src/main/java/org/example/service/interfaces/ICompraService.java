package org.example.service.interfaces;

import org.example.model.Compra;

import java.time.LocalDate;
import java.util.List;

public interface ICompraService extends IService<Compra>{
    void crearCompras(Long idCliente, List<Long> productoIds, LocalDate fecha);
}
