package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.inventario.Producto;
import com.backend.backend.services.inventario.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

    private final ProductoService productoService;

    private final ObjectMapper objectMapper;

    public ProductoController(ProductoService productoService, ObjectMapper objectMapper) {
        this.productoService = productoService;
        this.objectMapper = objectMapper;
    }

    @RequiresPermission("ACCESO_PRODUCTOS")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProductoById(@PathVariable Long id) {
        Producto producto = productoService.getProductoById(id);
        if (producto == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Producto no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Producto obtenido correctamente", producto));
    }

    @RequiresPermission("ACCESO_PRODUCTOS")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Object> findProductosByEmpresa(@PathVariable Long idEmpresa) {
        return ResponseEntity.ok(new HttpResponse(true, "Productos obtenidos correctamente", productoService.findProductosByEmpresa(idEmpresa)));
    }

    @RequiresPermission("ACCESO_PRODUCTOS")
    @PostMapping("/filter")
    public ResponseEntity<Object> findProductosDTOByFilter(@RequestBody ProductoFilter filter) {
        try {
            Page<ProductoDTO> productos = productoService.findProductosDTOByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Productos obtenidos correctamente", productos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_PRODUCTOS")
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> createProducto(@RequestPart("producto") String productoJson,
                                                 @RequestPart(value = "imagen", required = false) MultipartFile imagen,
                                                 @RequestHeader("idResponsable") Long idResponsable) {
        try {
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            productoService.createProducto(producto, imagen, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Producto creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_PRODUCTOS")
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> updateProducto(@PathVariable Long id,
                                                 @RequestPart("producto") String productoJson,
                                                 @RequestPart(value = "imagen", required = false) MultipartFile imagen,
                                                 @RequestHeader("idResponsable") Long idResponsable,
                                                 @PathParam("imageChanged") Boolean imageChanged) {
        try {
            Producto producto = objectMapper.readValue(productoJson, Producto.class);
            productoService.updateProducto(id, producto, imagen, idResponsable, imageChanged);
            return ResponseEntity.ok(new HttpResponse(true, "Producto actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
