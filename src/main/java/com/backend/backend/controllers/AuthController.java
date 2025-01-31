package com.backend.backend.controllers;

import com.backend.backend.dto.RegistroEmpleadoDTO;
import com.backend.backend.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegistroEmpleadoDTO registroEmpleadoDTO) {
        authService.registrarEmpleado(registroEmpleadoDTO);
        return new ResponseEntity<>("El registro se ha realizado correctamente", HttpStatus.CREATED);
    }
}
