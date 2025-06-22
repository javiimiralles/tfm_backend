package com.backend.backend.services.inventario;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.backend.backend.filters.CategoriaProductoFilter;
import com.backend.backend.models.inventario.CategoriaProducto;
import com.backend.backend.services.BaseTest;

public class CategoriaProductoServiceTest extends BaseTest {
    
    @Autowired
    private CategoriaProductoService categoriaProductoService;
    
    @BeforeEach
    public void setUp() {
        setUpBase();

        CategoriaProducto categoria1 = new CategoriaProducto();
        categoria1.setNombre("Categoria 1");
        categoria1.setDescripcion("Descripción de la categoría 1");
        categoria1.setIdEmpresa(idEmpresaBase);

        CategoriaProducto categoria2 = new CategoriaProducto();
        categoria2.setNombre("Categoria 2");
        categoria2.setDescripcion("Descripción de la categoría 2");
        categoria2.setIdEmpresa(idEmpresaBase);

        categoriaProductoService.createCategoriaProducto(categoria1, idUsuarioBase);
        categoriaProductoService.createCategoriaProducto(categoria2, idUsuarioBase);
    }

    @Test
    public void testGeneral() {
        Long idCategoria = testFindCategoriasProductosByFilter();
        testGetCategoriaProductoById(idCategoria);
        testUpdateCategoriaProducto(idCategoria);
        testDeleteCategoriaProducto(idCategoria);
    }

    private Long testFindCategoriasProductosByFilter() {
        CategoriaProductoFilter filter = new CategoriaProductoFilter();
        filter.setIdEmpresa(idEmpresaBase);
        filter.setNombre("Categoria");
        
        List<CategoriaProducto> categorias = categoriaProductoService.findCategoriasProductosByFilter(filter).getContent();
        assertNotNull(categorias);
        assertFalse(categorias.isEmpty());
        assertEquals(2, categorias.size());
        assertEquals("Categoria 1", categorias.get(0).getNombre());
        assertEquals("Categoria 2", categorias.get(1).getNombre());

        return categorias.get(0).getId();
    }

    private void testGetCategoriaProductoById(Long id) {
        CategoriaProducto categoria = categoriaProductoService.getCategoriaProductoById(id);
        assertNotNull(categoria);
        assertEquals("Categoria 1", categoria.getNombre());
    }

    private void testUpdateCategoriaProducto(Long id) {
        CategoriaProducto categoria = categoriaProductoService.getCategoriaProductoById(id);
        assertNotNull(categoria);
        categoria.setNombre("Categoria Actualizada");
        categoriaProductoService.updateCategoriaProducto(id, categoria, idUsuarioBase);
        
        CategoriaProducto updatedCategoria = categoriaProductoService.getCategoriaProductoById(id);
        assertNotNull(updatedCategoria);
        assertEquals("Categoria Actualizada", updatedCategoria.getNombre());
    }

    private void testDeleteCategoriaProducto(Long id) {
        categoriaProductoService.deleteCategoriaProducto(id, idUsuarioBase);
        CategoriaProducto deletedCategoria = categoriaProductoService.getCategoriaProductoById(id);
        assertNull(deletedCategoria);
    }
    
    
}
