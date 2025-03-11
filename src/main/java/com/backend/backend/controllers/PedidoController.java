package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.DatosPedido;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.enums.EstadoPedidoEnum;
import com.backend.backend.filters.PedidoFilter;
import com.backend.backend.models.ventas.Pedido;
import com.backend.backend.services.ventas.PedidoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final PedidoService pedidoService;

    private final ObjectMapper objectMapper;

    public PedidoController(PedidoService pedidoService, ObjectMapper objectMapper) {
        this.pedidoService = pedidoService;
        this.objectMapper = objectMapper;
    }

    @RequiresPermission("ACCESO_PEDIDOS")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getPedidoById(@PathVariable Long id) {
        Pedido pedido = pedidoService.getPedidoById(id);
        if (pedido == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Pedido no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Pedido obtenido correctamente", pedido));
    }

    @RequiresPermission("ACCESO_PEDIDOS")
    @PostMapping("/filter")
    public ResponseEntity<Object> findPedidosByFilter(@RequestBody PedidoFilter filter) {
        try {
            Page<Pedido> pedidos = pedidoService.findPedidosByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Pedidos obtenidos correctamente", pedidos));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_PRESPUESTOS")
    @PostMapping(value = "/presupuestos", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createPresupuesto(@RequestPart("datosPedido") String datosPedidoJson, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            DatosPedido datosPedido = objectMapper.readValue(datosPedidoJson, DatosPedido.class);
            pedidoService.createPresupuesto(datosPedido, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Presupuesto creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("ELIMINACION_PRESPUESTOS")
    @DeleteMapping("/presupuestos/{id}")
    public ResponseEntity<Object> cancelarPresupuesto(@PathVariable Long id, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            pedidoService.cancelarPresupuesto(id, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Presupuesto cancelado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_PEDIDOS")
    @PostMapping(value = "", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> createPedido(@RequestPart("datosPedido") String datosPedidoJson, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            DatosPedido datosPedido = objectMapper.readValue(datosPedidoJson, DatosPedido.class);
            pedidoService.createPedido(datosPedido, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Pedido creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_PEDIDOS")
    @PutMapping("/estado/{id}")
    public ResponseEntity<Object> updateEstadoPedido(@PathVariable Long id, @RequestParam("nuevoEstado") String nuevoEstado, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            pedidoService.updateEstadoPedido(id, EstadoPedidoEnum.getEstadoPedidoEnum(nuevoEstado), idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Estado del pedido actualizado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

}
