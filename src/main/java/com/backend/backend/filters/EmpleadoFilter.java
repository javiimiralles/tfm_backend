package com.backend.backend.filters;

import lombok.Data;

@Data
public class EmpleadoFilter {

    private Long idEmpresa;

    private Long idUsuario;

    private String nombre;

    private String apellidos;

    private String telefono;

    private String direccion;

    private String fechaNacimiento;

    private String genero;
}
