package com.backend.backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ClienteDTO {

    private Long id;

    private Long idEmpresa;

    private String nombre;

    private String apellidos;

    private String email;

    private Date fechaAlta;

}
