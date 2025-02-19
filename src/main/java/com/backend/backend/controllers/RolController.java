package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.Rol;
import com.backend.backend.services.RolService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
