package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.enums.EstadoPedidoProveedorEnum;
import com.backend.backend.filters.PedidoProveedorFilter;
import com.backend.backend.models.proveedores.PedidoProveedor;
import com.backend.backend.services.proveedores.PedidoProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos-proveedor")
public class PedidoProveedorController {

    private final PedidoProveedorService pedidoProveedorService;

    private final ObjectMapper objectMapper;

    public PedidoProveedorController(PedidoProveedorService pedidoProveedorService, ObjectMapper objectMapper) {
        this.pedidoProveedorService = pedidoProveedorService;
        this.objectMapper = objectMapper;
    }

    @RequiresPermission("ACCESO_PEDIDOS_PROVEEDORES")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPedidoProveedorById(@PathVariable Long id) {
        PedidoProveedor pedido = pedidoProveedorService.getPedidoProveedorById(id);
        if (pedido == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Pedido de proveedor no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Pedido de proveedor obtenido correctamente", pedido));
    }

    @RequiresPermission("ACCESO_PEDIDOS_PROVEEDORES")
    @PostMapping("/filter")
    public ResponseEntity<Object> findPedidosProveedorByFilter(@RequestBody PedidoProveedorFilter filter) {
        try {
            Page<PedidoProveedor> pedidos = pedidoProveedorService.findPedidosProveedorByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Pedidos de proveedor obtenidos correctamente", pedidos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_PEDIDOS_PROVEEDORES")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> realizarPedidoProveedor(@RequestPart("datosPedidoProveedor") String datosPedidoProveedorJson, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            DatosPedidoProveedor datosPedidoProveedor = objectMapper.readValue(datosPedidoProveedorJson, DatosPedidoProveedor.class);
            pedidoProveedorService.realizarPedidoProveedor(datosPedidoProveedor, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Pedido de proveedor realizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_PEDIDOS_PROVEEDORES")
    @PutMapping("/estado/{id}")
    public ResponseEntity<Object> updateEstadoPedidoProveedor(@PathVariable Long id, @RequestPart("nuevoEstado") String nuevoEstado) {
        try {
            pedidoProveedorService.updateEstadoPedidoProveedor(id, EstadoPedidoProveedorEnum.getEstadoPedidoProveedorEnum(nuevoEstado));
            return ResponseEntity.ok(new HttpResponse(true, "Estado del pedido de proveedor actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
