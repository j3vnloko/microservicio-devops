package com.vivitasol.mybackend;

import com.vivitasol.mybackend.entities.Categoria;
import com.vivitasol.mybackend.repositories.CategoriaRepository;
import com.vivitasol.mybackend.services.CategoriaServicesImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoriaServicesImplTest {

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private CategoriaServicesImpl categoriaServices;

    @Test
    void crearCategoria_retornaCategoria() {
        Categoria categoria = new Categoria(null, "Electrónica", null);
        when(categoriaRepository.save(categoria)).thenReturn(categoria);

        Categoria resultado = categoriaServices.crear(categoria);

        assertNotNull(resultado);
        assertEquals("Electrónica", resultado.getNombre());
        verify(categoriaRepository, times(1)).save(categoria);
    }

    @Test
    void obtenerId_retornaCategoria_siExiste() {
        Categoria categoria = new Categoria(1L, "Ropa", null);
        when(categoriaRepository.findById(1L)).thenReturn(Optional.of(categoria));

        Categoria resultado = categoriaServices.obtenerId(1L);

        assertNotNull(resultado);
        assertEquals("Ropa", resultado.getNombre());
    }

    @Test
    void obtenerId_lanzaExcepcion_siNoExiste() {
        when(categoriaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> categoriaServices.obtenerId(99L));
    }

    @Test
    void listarTodas_retornaLista() {
        List<Categoria> lista = List.of(
            new Categoria(1L, "Electrónica", null),
            new Categoria(2L, "Ropa", null)
        );
        when(categoriaRepository.findAll()).thenReturn(lista);

        List<Categoria> resultado = categoriaServices.listarTodas();

        assertEquals(2, resultado.size());
    }

    @Test
    void eliminar_lanzaExcepcion_siNoExiste() {
        when(categoriaRepository.existsById(99L)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> categoriaServices.eliminar(99L));
    }
}