package org.example.service.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * Interface para servicio de Usuario
 * Implementa principio Interface Segregation
 */
public interface IService<T> {
    void crear(T t);
    Optional<T> obtenerPorId(Long id);
    List<T> obtenerTodos();
    void actualizar(T t);
    void eliminar(Long id);
}
