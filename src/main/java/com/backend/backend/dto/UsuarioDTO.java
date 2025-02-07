package com.backend.backend.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UsuarioDTO {

    private Long id;

    private String email;

    private String rol;

    private Set<String> permisos;
}
