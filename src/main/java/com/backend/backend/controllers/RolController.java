package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.RolFilter;
import com.backend.backend.models.roles.Rol;
import com.backend.backend.services.roles.RolService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @RequiresPermission("ACCESO_ROLES")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Object> findRolesByIdEmpresa(@PathVariable Long idEmpresa) {
        List<Rol> roles = rolService.findRolesByIdEmpresa(idEmpresa);
        return ResponseEntity.ok(new HttpResponse(true, "Roles obtenidos correctamente", roles));
    }

    @RequiresPermission("ACCESO_ROLES")
    @PostMapping("/filter")
    public ResponseEntity<Object> findRolesByFilter(@RequestBody RolFilter filter) {
        try {
            Page<Rol> roles = rolService.findRolesByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Roles obtenidos correctamente", roles));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
