package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.CategoriaProductoFilter;
import com.backend.backend.models.inventario.CategoriaProducto;
import com.backend.backend.services.inventario.CategoriaProductoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categorias-productos")
public class CategoriaProductoController {

    private final CategoriaProductoService categoriaProductoService;

    public CategoriaProductoController(CategoriaProductoService categoriaProductoService) {
        this.categoriaProductoService = categoriaProductoService;
    }

    @RequiresPermission("ACCESO_CATEGORIAS_PRODUCTOS")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoriaProductoById(@PathVariable Long id) {
        CategoriaProducto categoriaProducto = categoriaProductoService.getCategoriaProductoById(id);
        if (categoriaProducto == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Categoría no encontrada"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Categoría obtenida correctamente", categoriaProducto));
    }

    @RequiresPermission("ACCESO_CATEGORIAS_PRODUCTOS")
    @PostMapping("/filter")
    public ResponseEntity<Object> findCategoriasProductosByFilter(@RequestBody CategoriaProductoFilter filter) {
        try {
            Page<CategoriaProducto> categoriaProductos = categoriaProductoService.findCategoriasProductosByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Categorías obtenidas correctamente", categoriaProductos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_CATEGORIAS_PRODUCTOS")
    @PostMapping("")
    public ResponseEntity<Object> createCategoriaProducto(@RequestBody CategoriaProducto categoriaProducto, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            categoriaProductoService.createCategoriaProducto(categoriaProducto, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Categoría creada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_CATEGORIAS_PRODUCTOS")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCategoriaProducto(@PathVariable Long id, @RequestBody CategoriaProducto categoriaProducto, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            categoriaProductoService.updateCategoriaProducto(id, categoriaProducto, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Categoría actualizada correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
