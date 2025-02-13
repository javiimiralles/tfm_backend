package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.Empleado;
import com.backend.backend.services.EmpleadoService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @RequiresPermission("ACCESO_EMPLEADOS")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getEmpleadoById(@PathVariable Long id) {
        Empleado empleado = empleadoService.getEmpleadoById(id);
        if (empleado == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Empleado no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Empleado obtenido correctamente", empleado));
    }

    @RequiresPermission("ACCESO_EMPLEADOS")
    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<Object> getEmpleadoByIdUsuario(@PathVariable Long idUsuario) {
        Empleado empleado = empleadoService.getEmpleadoByIdUsuario(idUsuario);
        if (empleado == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Empleado no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Empleado obtenido correctamente", empleado));
    }

    @RequiresPermission("ACCESO_EMPLEADOS")
    @PostMapping("/filter")
    public ResponseEntity<Object> getEmpleadosDTOByFilter(@RequestBody EmpleadoFilter filter) {
        Page<EmpleadoDTO> empleados = empleadoService.findEmpleadosDTOByFilter(filter);
        return ResponseEntity.ok(new HttpResponse(true, "Empleados obtenidos correctamente", empleados));
    }
}
