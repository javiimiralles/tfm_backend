package com.backend.backend.controllers;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.HttpResponse;
import com.backend.backend.models.Cliente;
import com.backend.backend.services.ClienteService;
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
    @GetMapping("/empresa/{idEmpresa}")
    public ResponseEntity<Object> getClientesByEmpresa(@PathVariable Long idEmpresa) {
        List<Cliente> clientes = clienteService.findClientesByEmpresa(idEmpresa);
        return ResponseEntity.ok(new HttpResponse(true, "Clientes obtenidos correctamente", clientes));
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
}
