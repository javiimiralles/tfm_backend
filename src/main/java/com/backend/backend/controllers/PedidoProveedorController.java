package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.DatosPedidoProveedor;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.services.proveedores.PedidoProveedorService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}
