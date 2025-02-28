package com.backend.backend.security;

import com.backend.backend.annotations.RequiresPermission;
import com.backend.backend.dto.UsuarioDTO;
import com.backend.backend.services.usuarios.UsuarioService;
import com.backend.backend.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

@Component
public class PermissionInterceptor implements HandlerInterceptor {

    private final JWTUtil jwtUtil;
    private final UsuarioService usuarioService;

    public PermissionInterceptor(JWTUtil jwtUtil, @Lazy UsuarioService usuarioService) {
        this.jwtUtil = jwtUtil;
        this.usuarioService = usuarioService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true; // Si no es un endpoint, permite el acceso
        }

        RequiresPermission requiresPermission = handlerMethod.getMethodAnnotation(RequiresPermission.class);
        if (requiresPermission == null) {
            return true; // Si el endpoint no tiene anotación, permite el acceso
        }

        String token = request.getHeader("x-token");
        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token no proporcionado");
            return false;
        }

        Claims claims = jwtUtil.extractClaimsFromToken(token);
        Long idUsuario = Long.parseLong(claims.getSubject());
        UsuarioDTO usuario = usuarioService.getUsuarioDTOById(idUsuario);

        if (usuario == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Usuario no encontrado");
            return false;
        }

        Set<String> permisos = usuario.getPermisos();
        String permisoRequerido = requiresPermission.value();

        // Verificar si el usuario tiene el permiso necesario
        if (!permisos.contains(permisoRequerido)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN, "No tienes permiso para realizar esta acción");
            return false;
        }

        return true;
    }
}
