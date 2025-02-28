package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.ClienteDTO;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.filters.ClienteFilter;
import com.backend.backend.models.clientes.Cliente;
import com.backend.backend.services.clientes.ClienteService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @RequiresPermission("ACCESO_CLIENTES")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getClienteById(@PathVariable Long id) {
        Cliente cliente = clienteService.getClienteById(id);
        if (cliente == null) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, "Cliente no encontrado"));
        }
        return ResponseEntity.ok(new HttpResponse(true, "Cliente obtenido correctamente", cliente));
    }

    @RequiresPermission("ACCESO_CLIENTES")
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Object> findClientesByEmpresa(@PathVariable Long idEmpresa) {
        List<Cliente> clientes = clienteService.findClientesByEmpresa(idEmpresa);
        return ResponseEntity.ok(new HttpResponse(true, "Clientes obtenidos correctamente", clientes));
    }

    @RequiresPermission("ACCESO_CLIENTES")
    @PostMapping("/filter")
    public ResponseEntity<Object> findClientesDTOByFilter(@RequestBody ClienteFilter filter) {
        try {
            Page<ClienteDTO> clientes = clienteService.findClientesDTOByFilter(filter);
            return ResponseEntity.ok(new HttpResponse(true, "Clientes obtenidos correctamente", clientes));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("CREACION_CLIENTES")
    @PostMapping("")
    public ResponseEntity<Object> createCliente(@RequestBody Cliente cliente, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            clienteService.createCliente(cliente, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Cliente creado correctamente", cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("EDICION_CLIENTES")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateCliente(@PathVariable Long id, @RequestBody Cliente cliente, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            clienteService.updateCliente(id, cliente, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Cliente actualizado correctamente", cliente));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }

    @RequiresPermission("ELIMINACION_CLIENTES")
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCliente(@PathVariable Long id, @RequestHeader("idResponsable") Long idResponsable) {
        try {
            clienteService.deleteCliente(id, idResponsable);
            return ResponseEntity.ok(new HttpResponse(true, "Cliente eliminado correctamente"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new HttpResponse(false, e.getMessage()));
        }
    }
}
