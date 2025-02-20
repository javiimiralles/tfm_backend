package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.dto.ProductoDTO;
import com.backend.backend.filters.ProductoFilter;
import com.backend.backend.models.Producto;
import com.backend.backend.services.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
