package com.vivitasol.mybackend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.vivitasol.mybackend.entities.Categoria;
import com.vivitasol.mybackend.repositories.CategoriaRepository;

@Service
public class CategoriaServicesImpl implements CategoriaServices {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public Categoria crear(Categoria unaCategoria){
        return categoriaRepository.save(unaCategoria);
    }

    @Override
    public Categoria obtenerId(Long id){
        return categoriaRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
    }

    @Override
    public List<Categoria> listarTodas(){
        return (List<Categoria>) categoriaRepository.findAll();
    }

    @Override
    public void eliminar(Long id){
        if (!categoriaRepository.existsById(id)){
            throw new RuntimeException("Categoria no encontrada");
        }
        categoriaRepository.deleteById(id);
    }

    @Override
    public Categoria actualizar(Long id, Categoria categoriaActualizada){
        Categoria existe = obtenerId(id);
        existe.setNombre(categoriaActualizada.getNombre());
        return categoriaRepository.save(existe);
    }


}
