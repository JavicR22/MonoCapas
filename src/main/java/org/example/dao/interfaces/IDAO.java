package org.example.dao.interfaces;

import org.example.model.Usuario;

import java.util.List;
import java.util.Optional;

public interface IDAO <T>{
    void crear(T t);
    Optional<T> obtenerPorId(Long id);
    List<T> obtenerTodos();
    void eliminar(Long id);

    void actualizar(T t);
}
