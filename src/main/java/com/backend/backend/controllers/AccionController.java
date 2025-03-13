package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.roles.Accion;
import com.backend.backend.services.roles.AccionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/acciones")
public class AccionController {

    private final AccionService accionService;

    public AccionController(AccionService accionService) {
        this.accionService = accionService;
    }

    @RequiresPermission("ACCESO_ROLES")
    @GetMapping("")
    public ResponseEntity<Object> getAcciones() {
        List<Accion> acciones = accionService.getAcciones();
        return ResponseEntity.ok(new HttpResponse(true, "Acciones obtenidas correctamente", acciones));
    }
}
