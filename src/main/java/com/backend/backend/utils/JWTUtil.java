package com.backend.backend.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long idUsuario, String rol, Set<String> accionesPermitidas) {
        return Jwts.builder()
                .subject(idUsuario.toString())
                .claim("rol", rol)
                .claim("acciones", accionesPermitidas)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 31536000000L))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .compact();
    }

    public Claims extractClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public Long getUserIdFromToken(String token) {
        return Long.parseLong(extractClaimsFromToken(token).getSubject());
    }

    public String getRolFromToken(String token) {
        return extractClaimsFromToken(token).get("rol", String.class);
    }

    public Set<String> getAccionesFromToken(String token) {
        return extractClaimsFromToken(token).get("acciones", Set.class);
    }
}
