package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.dto.ProveedorDTO;
import com.backend.backend.filters.ProveedorFilter;
import com.backend.backend.models.proveedores.Proveedor;
import com.backend.backend.services.proveedores.ProveedorService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/proveedores")
public class ProveedorController {

    private final ProveedorService proveedorService;

    public ProveedorController(ProveedorService proveedorService) {
        this.proveedorService = proveedorService;
    }

    @RequiresPermission("ACCESO_PROVEEDORES")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getProveedorById(@PathVariable Long id) {
        Proveedor proveedor = proveedorService.getProveedorById(id);
        if (proveedor == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Proveedor no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Proveedor obtenido correctamente", proveedor));
    }

    @RequiresPermission("ACCESO_PROVEEDORES")
    @PostMapping("/filter")
    public ResponseEntity<Object> findProveedoresDTOByFilter(@RequestBody ProveedorFilter filter) {
        try {
            Page<ProveedorDTO> proveedores = proveedorService.findProveedoresDTOByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Proveedores obtenidos correctamente", proveedores));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_PROVEEDORES")
    @PostMapping(value = "")
    public ResponseEntity<Object> createProveedor(@RequestBody Proveedor proveedor, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            proveedorService.createProveedor(proveedor, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Proveedor creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_PROVEEDORES")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateProveedor(@PathVariable Long id, @RequestBody Proveedor proveedor, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            proveedorService.updateProveedor(id, proveedor, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Proveedor actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("ELIMINACION_PROVEEDORES")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteProveedor(@PathVariable Long id, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            proveedorService.deleteProveedor(id, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Proveedor eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

}
