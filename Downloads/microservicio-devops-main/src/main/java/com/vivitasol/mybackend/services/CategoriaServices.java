package com.vivitasol.mybackend.services;

import java.util.List;

import com.vivitasol.mybackend.entities.Categoria;

public interface CategoriaServices {

    Categoria crear(Categoria unaCategoria);
    Categoria obtenerId(Long id);
    List<Categoria> listarTodas();
    void eliminar(Long id);
    Categoria actualizar(Long id, Categoria categoriaActualizada);
}
