package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.proveedores.DetallePedidoProveedor;
import com.backend.backend.services.proveedores.DetallePedidoProveedorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido-proveedor")
public class DetallePedidoProveedorController {

    private final DetallePedidoProveedorService detallePedidoProveedorService;

    public DetallePedidoProveedorController(DetallePedidoProveedorService detallePedidoProveedorService) {
        this.detallePedidoProveedorService = detallePedidoProveedorService;
    }

    @RequiresPermission("ACCESO_PEDIDOS_PROVEEDORES")
    @GetMapping("/pedido/{idPedidoProveedor}")
    public ResponseEntity<Object> findDetallesPedidoProveedorByIdPedidoProveedor(@PathVariable Long idPedidoProveedor) {
        List<DetallePedidoProveedor> detalles = detallePedidoProveedorService.findDetallesPedidoProveedorByIdPedidoProveedor(idPedidoProveedor);
        return ResponseEntity.ok(new HttpResponse(true, "Detalles de pedido proveedor obtenidos correctamente", detalles));
    }
}
