package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.EmpleadoDTO;
import com.backend.backend.dto.EmpleadoForm;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.EmpleadoFilter;
import com.backend.backend.models.empleados.Empleado;
import com.backend.backend.models.usuarios.Usuario;
import com.backend.backend.services.empleados.EmpleadoService;
import com.backend.backend.services.usuarios.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    private final ObjectMapper objectMapper;

    private final UsuarioService usuarioService;

    public EmpleadoController(EmpleadoService empleadoService, ObjectMapper objectMapper, UsuarioService usuarioService) {
        this.empleadoService = empleadoService;
        this.objectMapper = objectMapper;
        this.usuarioService = usuarioService;
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
    public ResponseEntity<Object> findEmpleadosDTOByFilter(@RequestBody EmpleadoFilter filter) {
        try {
            Page<EmpleadoDTO> empleados = empleadoService.findEmpleadosDTOByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Empleados obtenidos correctamente", empleados));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_EMPLEADOS")
    @PostMapping(value = "", consumes = {"multipart/form-data"})
    public ResponseEntity<Object> createEmpleado(@RequestPart("empleado") String empleadoFormJson,
                                                 @RequestPart(value = "imagen", required = false) MultipartFile imagen,
                                                 @RequestHeader("idResponsable") Long idResponsable) {
        try {
            EmpleadoForm empleadoForm = objectMapper.readValue(empleadoFormJson, EmpleadoForm.class);
            Usuario usuario = mapEmpleadoFormToUsuario(empleadoForm);
            Empleado empleado = mapEmpleadoFormToEmpleado(empleadoForm);
            empleadoService.createEmpleado(empleado, usuario, imagen, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Empleado creado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    private Usuario mapEmpleadoFormToUsuario(EmpleadoForm empleadoForm) {
        Usuario usuario = new Usuario();
        usuario.setEmail(empleadoForm.getEmail());
        usuario.setPassword(empleadoForm.getPassword());
        usuario.setRol(empleadoForm.getRol());
        return usuario;
    }

    private Empleado mapEmpleadoFormToEmpleado(EmpleadoForm empleadoForm) {
        Empleado empleado = new Empleado();
        empleado.setIdEmpresa(empleadoForm.getIdEmpresa());
        empleado.setNombre(empleadoForm.getNombre());
        empleado.setApellidos(empleadoForm.getApellidos());
        empleado.setNif(empleadoForm.getNif());
        empleado.setTelefono(empleadoForm.getTelefono());
        empleado.setDireccion(empleadoForm.getDireccion());
        empleado.setPais(empleadoForm.getPais());
        empleado.setFechaNacimiento(empleadoForm.getFechaNacimiento());
        empleado.setGenero(empleadoForm.getGenero());
        empleado.setProvincia(empleadoForm.getProvincia());
        empleado.setPoblacion(empleadoForm.getPoblacion());
        empleado.setCodigoPostal(empleadoForm.getCodigoPostal());
        return empleado;
    }
}
