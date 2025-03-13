package com.backend.backend.dto;

import com.backend.backend.models.roles.Accion;
import lombok.Data;

import java.util.List;

@Data
public class RolForm {

    private Long idEmpresa;

    private String nombre;

    private String descripcion;

    private List<Accion> acciones;
}
