package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.ventas.DetallePedido;
import com.backend.backend.services.ventas.DetallePedidoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/detalles-pedido")
public class DetallePedidoController {

    private final DetallePedidoService detallePedidoService;

    public DetallePedidoController(DetallePedidoService detallePedidoService) {
        this.detallePedidoService = detallePedidoService;
    }

    @RequiresPermission("ACCESO_PEDIDOS")
    @GetMapping("/pedido/{idPedido}")
    public ResponseEntity<Object> findDetallesPedidoByIdPedido(@PathVariable Long idPedido) {
        List<DetallePedido> detalles = detallePedidoService.findDetallesPedidoByIdPedido(idPedido);
        return ResponseEntity.ok(new HttpResponse(true, "Detalles de pedido obtenidos correctamente", detalles));
    }
}
