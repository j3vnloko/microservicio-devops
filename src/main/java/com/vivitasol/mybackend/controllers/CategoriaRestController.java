package com.vivitasol.mybackend.controllers;
// Endpoint para gestión de categorías - feature agregada

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vivitasol.mybackend.entities.Categoria;
import com.vivitasol.mybackend.services.CategoriaServices;

@RequestMapping("/api/categorias")
@RestController
public class CategoriaRestController {

    @Autowired
    private CategoriaServices categoriaServices;


    @PostMapping
    public ResponseEntity<Categoria> crearCategoria(@RequestBody Categoria unaCategoria){
        Categoria nuevCategoria = categoriaServices.crear(unaCategoria);
        return ResponseEntity.ok(nuevCategoria);            //201
    }


    @GetMapping("/{id}")
    public ResponseEntity<Categoria> obtenerCategoriaId(@PathVariable Long id){
        Categoria unaCategoria = categoriaServices.obtenerId(id);
        return ResponseEntity.ok(unaCategoria);             //200
    }


    @GetMapping
    public ResponseEntity<List<Categoria>> listarTodas(){
        List<Categoria> categorias = categoriaServices.listarTodas();
        return ResponseEntity.ok(categorias);               //200
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarId(@PathVariable Long id){
        categoriaServices.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Categoria> actualizarCategoria(@PathVariable Long id, @RequestBody Categoria categoriaActualizada){
        Categoria categoriaNueva = categoriaServices.actualizar(id, categoriaActualizada);
        return ResponseEntity.ok(categoriaNueva);
    }



}
