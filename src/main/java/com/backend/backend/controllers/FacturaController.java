package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.FacturaFilter;
import com.backend.backend.models.ventas.Factura;
import com.backend.backend.services.pdf.PdfGeneratorService;
import com.backend.backend.services.ventas.FacturaService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    private final PdfGeneratorService pdfGeneratorService;

    public FacturaController(FacturaService facturaService, PdfGeneratorService pdfGeneratorService) {
        this.facturaService = facturaService;
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @RequiresPermission("ACCESO_FACTURAS")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getFacturaById(@PathVariable Long id) {
        Factura factura = facturaService.getFacturaById(id);
        if (factura == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Factura no encontrada"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Factura obtenida correctamente", factura));
    }

    @RequiresPermission("ACCESO_FACTURAS")
    @PostMapping("/filter")
    public ResponseEntity<Object> findFacturasByFilter(@RequestBody FacturaFilter filter) {
        try {
            Page<Factura> facturas = facturaService.findFacturasByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Facturas obtenidas correctamente", facturas));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_FACTURAS")
    @PostMapping
    public ResponseEntity<Object> createFactura(@RequestBody Factura factura, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            byte[] pdf = facturaService.createFactura(factura, idResponsable);
            return ResponseEntity.ok(pdf);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("ACCESO_FACTURAS")
    @GetMapping("/pdf/{idFactura}")
    public ResponseEntity<byte[]> getFacturaPdf(@PathVariable Long idFactura) {
        try {
            byte[] pdf = pdfGeneratorService.generateFacturaPdf(idFactura);
            return ResponseEntity.ok(pdf);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
