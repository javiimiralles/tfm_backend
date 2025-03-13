package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.roles.Permiso;
import com.backend.backend.services.roles.PermisoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/permisos")
public class PermisoController {

    private final PermisoService permisoService;

    public PermisoController(PermisoService permisoService) {
        this.permisoService = permisoService;
    }

    @RequiresPermission("ACCESO_ROLES")
    @GetMapping("/rol/{idRol}")
    public ResponseEntity<Object> findPermisosByRolId(@PathVariable Long idRol) {
        List<Permiso> permisos = permisoService.findPermisosByRolId(idRol);
        return ResponseEntity.ok(new HttpResponse(true, "Permisos obtenidos correctamente", permisos));
    }
}
