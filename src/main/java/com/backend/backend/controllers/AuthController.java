package com.backend.backend.controllers;

import com.backend.backend.dto.HttpResponse;
import com.backend.backend.dto.LoginUsuarioForm;
import com.backend.backend.dto.RegistroEmpleadoForm;
import com.backend.backend.models.Usuario;
import com.backend.backend.services.AuthService;
import com.backend.backend.services.UsuarioServiceImpl;
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
    private final UsuarioServiceImpl usuarioServiceImpl;

    public AuthController(AuthService authService, JWTUtil jwtUtil, UsuarioServiceImpl usuarioServiceImpl) {
        this.authService = authService;
        this.jwtUtil = jwtUtil;
        this.usuarioServiceImpl = usuarioServiceImpl;
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@Valid @RequestBody RegistroEmpleadoForm registroEmpleadoForm) {
        authService.registrarEmpleado(registroEmpleadoForm);
        return ResponseEntity.ok(new HttpResponse(true, "Empleado registrado correctamente"));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@Valid @RequestBody LoginUsuarioForm loginUsuarioForm) {
        try {
            Usuario usuario = authService.authenticate(loginUsuarioForm);
            usuario.setPassword(null);
            String token = jwtUtil.generateToken(usuario.getId());
            return ResponseEntity.ok(new HttpResponse(true, "Inicio de sesión correcto", usuario, token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponse(false, e.getMessage(), null));
        }
    }

    @GetMapping("/refresh-token")
    public ResponseEntity<Object> refreshToken(@RequestHeader("x-token") String token) {
        try {
            Usuario usuario = authService.verifyToken(token);
            usuario.setPassword(null);
            String newToken = jwtUtil.generateToken(usuario.getId());
            return ResponseEntity.ok(new HttpResponse(true, "Token actualizado", usuario, newToken));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HttpResponse(false, e.getMessage(), null));
        }
    }


}
