package com.backend.backend.controllers;

import com.backend.backend.dto.HttpResponse;
import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroUsuarioForm;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.services.AuthService;
import com.backend.backend.utils.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwtUtil;

    public AuthController(AuthService authService, JWTUtil jwtUtil) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegistroUsuarioForm registroUsuarioForm) {
        authService.register(registroUsuarioForm);
        return ResponseEntity.ok(new HttpResponse(true, "Empleado registrado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUsuarioForm loginUsuarioForm) {
        try {
            UsuarioDTO usuarioDTO = authService.authenticate(loginUsuarioForm);
            String token = jwtUtil.generateToken(usuarioDTO.getId(), usuarioDTO.getRol(), usuarioDTO.getPermisos());
            return ResponseEntity.ok(new HttpResponse(true, "Inicio de sesión correcto", usuarioDTO, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestHeader("x-token") String token) {
        try {
            UsuarioDTO usuarioDTO = authService.verifyToken(token);
            String newToken = jwtUtil.generateToken(usuarioDTO.getId(), usuarioDTO.getRol(), usuarioDTO.getPermisos());
            return ResponseEntity.ok(new HttpResponse(true, "Token actualizado", usuarioDTO, newToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponse(false, e.getMessage(), null));
        }
    }

}
