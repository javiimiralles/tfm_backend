package com.backend.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class EmpleadoDTO {

    private Long id;

    private Long idEmpresa;

    private String nombre;

    private String apellidos;

    private String rol;

    private Date fechaAlta;

    private Date fechaBaja;
}
